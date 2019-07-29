package com.ziwu.controller.off;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ziwu.pojo.Grade;
import com.ziwu.pojo.GradeAllocation;
import com.ziwu.pojo.GradeType;
import com.ziwu.pojo.RegularGrade;
import com.ziwu.pojo.RespBean;
import com.ziwu.pojo.StudentCourse;
import com.ziwu.pojo.TeacherCourse;
import com.ziwu.service.GradeService;
import com.ziwu.service.StudentCourseService;
import com.ziwu.service.TeacherCourseService;

@RestController
@RequestMapping("/office/teacher")
public class OffTeacherCourseController {

	@Autowired
	TeacherCourseService teacherCourseService;
	
	@Autowired
	StudentCourseService studentCourseService;
	
	@Autowired
	GradeService gradeService;
	
	@RequestMapping(value = "/course", method = RequestMethod.GET)
    public Map<String, Object> getTeacherCourseByPage(
    		@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String keywords) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("controller");
		Long count = teacherCourseService.getCountByKeywords(keywords);
		System.out.println("count:"+count);
		List<TeacherCourse> teacherCourseByPage = teacherCourseService.getTeacherCourseByPage(page, size, keywords);
		map.put("teacherCourses", teacherCourseByPage);
		map.put("count", count);
		return map;
	}
	
	@RequestMapping(value = "/course/{ids}", method = RequestMethod.DELETE)
	public RespBean deleteTeacherCourse(@PathVariable String ids) {
		System.out.println(ids);
		if (teacherCourseService.deleteTeacherCourse(ids)) {
			return RespBean.ok("删除成功!");
		}
		return RespBean.error("删除失败!");
	}
	
	@RequestMapping(value = "/ins", method = RequestMethod.POST)
	public RespBean insTeacherCourse(Integer[] user_ids,Integer[] course_ids) {
		int i=teacherCourseService.insertTeacherCourse(user_ids, course_ids);
		if (i == user_ids.length * course_ids.length) {
			return RespBean.ok("课程分配成功!");
		} else if (i == -1) {
			return RespBean.error("课程分配重复，分配失败!");
		}
		return RespBean.error("分配失败!");
	}
	
	@RequestMapping("/teacherCourse")
	public List<TeacherCourse> getAllTeacherCourse() {
		return teacherCourseService.getAllTeacherCourse();
	}
	
	@RequestMapping(value = "/getTotalGrade", method = RequestMethod.GET)
	public List<Grade> getTotalGradeByTeacherCourseId(Integer teacher_course_id) {
		return gradeService.getTotalGradeByTeacherCourseId(teacher_course_id);
	}
	
	@RequestMapping(value = "/Grade/", method = RequestMethod.PUT)
	public RespBean updateTotalGrade(Grade grade) {
		if (gradeService.updateTotalGrade(grade) == 1) {
			return RespBean.ok("修改成功!");
		}
		return RespBean.error("修改失败!");
	}
	
	@RequestMapping(value = "/computeAllGrade", method = RequestMethod.PUT)
	public RespBean computeAllGrade(Integer teacher_course_id, Integer user_id) {
		List<GradeType> gradeTypes = gradeService.getAllGradeType();

		List<TeacherCourse> teacherCourses = teacherCourseService.getTeacherCourseById(user_id);

		List<StudentCourse> studentCourses = studentCourseService.getStudentByTeacherCourseId(teacher_course_id);

		List<GradeAllocation> gradeAllocations = gradeService.getAllocationByTeacherCourseId(teacher_course_id);

		float thisProportion = 0;

		for (int j = 0; j < teacherCourses.size(); j++) {
			TeacherCourse thisTeacherCourse = teacherCourses.get(j);
			if (thisTeacherCourse.getId() == teacher_course_id) {
				thisProportion = thisTeacherCourse.getCourse().getProportion();
			}
		}
		System.out.println("thisProportion:" + thisProportion);

		System.out.println("studentCourses.size():" + studentCourses.size());
		for (int i = 0; i < studentCourses.size(); i++) {
			System.out.println("第" + i + "次     studentCourses");
			float[][] regular = new float[gradeTypes.size()][2]; // each type sum,number

			StudentCourse thisStudent = studentCourses.get(i);
			System.out.println("thisStudent.getId():" + thisStudent.getId());

			List<RegularGrade> regularGrades = gradeService.getRegularGradeById(thisStudent.getId());

			float regularSum = 0;
			float thisFinal = 0;
			float thisTotal = 0;

			for (int j = 0; j < regularGrades.size(); j++) {
				System.out.println("第" + j + "次     regularGrades");
				RegularGrade thisRegularGrade = regularGrades.get(j);

				System.out.println("b00: " + regular[0][0] + "   b01: " + regular[0][1]);
				System.out.println("b10: " + regular[1][0] + "   b11: " + regular[1][1]);
				regular[thisRegularGrade.getType_id() - 1][0] = regular[thisRegularGrade.getType_id() - 1][0]
						+ thisRegularGrade.getGrade();
				regular[thisRegularGrade.getType_id() - 1][1] = regular[thisRegularGrade.getType_id() - 1][1] + 1;
				System.out.println("a00: " + regular[0][0] + "   a01: " + regular[0][1]);
				System.out.println("a10: " + regular[1][0] + "   a11: " + regular[1][1]);
			}

			for (int k = 0; k < gradeAllocations.size(); k++) {
				System.out.println("第" + k + "次     gradeAllocations");
				GradeAllocation thisGradeAllocation = gradeAllocations.get(k);

				System.out.println("thisGradeAllocation.getType_id():" + thisGradeAllocation.getType_id());
				System.out.println("thisGradeAllocation.getAllocation():" + thisGradeAllocation.getAllocation());
				regularSum = regularSum + regular[thisGradeAllocation.getType_id() - 1][0]
						/ regular[thisGradeAllocation.getType_id() - 1][1] * thisGradeAllocation.getAllocation();
			}
			System.out.println("regularSum:" + regularSum);

			List<Grade> grades = gradeService.getFianlGradeById(thisStudent.getId());
			System.out.println("grades:" + grades);
			
			if (grades.size() == 0) {
				gradeService.addFinalGrade(thisStudent.getId(), 0);
			} else {
				thisFinal = grades.get(0).getFinal_grade();
			}
			System.out.println("thisFinal:" + thisFinal);
			
			thisTotal = regularSum * thisProportion + thisFinal * (1 - thisProportion);
			System.out.println("thisTotal:" + thisTotal);

			Grade thisGrade = new Grade();
			thisGrade.setUser_course_id(thisStudent.getId());
			thisGrade.setRegular_grade(regularSum);
			thisGrade.setFinal_grade(thisFinal);
			thisGrade.setTotal_grade(thisTotal);

			gradeService.updateTotalGrade(thisGrade);
		}
		return RespBean.ok("更新成功!");
	}
}
