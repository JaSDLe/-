package com.ziwu.controller.tea;

import java.util.List;
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
@RequestMapping("/teacher/course")
public class TeaCourseController {

	@Autowired
	TeacherCourseService teacherCourseService;

	@Autowired
	StudentCourseService studentCourseService;

	@Autowired
	GradeService gradeService;

	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public List<TeacherCourse> getTeacherCourseById(@RequestParam(required = true) Integer id) {
		return teacherCourseService.getTeacherCourseById(id);
	}

	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public List<StudentCourse> getStudentByTeacherCourseId(@RequestParam(required = true) Integer id) {
		return studentCourseService.getStudentByTeacherCourseId(id);
	}

	@RequestMapping(value = "/allocation", method = RequestMethod.PUT)
	public RespBean updateAllocationByTeacherCourseId(Integer teacher_course_id, float[] allocations) {
		System.out.println("teacher_course_id:" + teacher_course_id);
		System.out.println("allocations:" + allocations.length);
		if (gradeService.updateAllocationByTeacherCourseId(teacher_course_id, allocations) == 1) {
			return RespBean.ok("修改成功!");
		}
		return RespBean.error("修改失败!");
	}

	@RequestMapping(value = "/getAllocation", method = RequestMethod.GET)
	public List<GradeAllocation> getAllocationByTeacherCourseId(
			@RequestParam(required = true) Integer teacher_course_id) {
		return gradeService.getAllocationByTeacherCourseId(teacher_course_id);
	}

	@RequestMapping(value = "/addRegularGrade", method = RequestMethod.POST)
	public RespBean addRegularGrade(Integer user_course_id, Integer number, float grade, Integer type_id) {
		System.out.println("user_course_id:" + user_course_id);
		System.out.println("number:" + number);
		System.out.println("grade:" + grade);
		System.out.println("type_id:" + type_id);
		int i = gradeService.addRegularGrade(user_course_id, number, grade, type_id);
		if (i == 1) {
			return RespBean.ok("添加成绩成功!");
		} else if (i == -1) {
			return RespBean.error("成绩重复，添加失败!");
		}
		return RespBean.error("添加成绩失败!");
	}

	@RequestMapping(value = "/getRegularGrade", method = RequestMethod.GET)
	public List<RegularGrade> getRegularGrade(@RequestParam(required = true) Integer teacher_course_id) {
		return gradeService.getRegularGrade(teacher_course_id);
	}

	@RequestMapping(value = "/RegularGrade/{ids}", method = RequestMethod.DELETE)
	public RespBean deleteGrade(@PathVariable String ids) {
		if (gradeService.deleteGrade(ids)) {
			return RespBean.ok("删除成功!");
		}
		return RespBean.error("删除失败!");
	}

	@RequestMapping(value = "/RegularGrade/", method = RequestMethod.PUT)
	public RespBean updateGrade(RegularGrade regularGrade) {
		if (gradeService.updateGrade(regularGrade) == 1) {
			return RespBean.ok("修改成功!");
		}
		return RespBean.error("修改失败!");
	}

	@RequestMapping(value = "/addFinalGrade", method = RequestMethod.POST)
	public RespBean addFinalGrade(Integer user_course_id, float final_grade) {
		System.out.println("user_course_id:" + user_course_id);
		System.out.println("final_grade:" + final_grade);
		int i = gradeService.addFinalGrade(user_course_id, final_grade);
		if (i == 1) {
			return RespBean.ok("添加成绩成功!");
		} else if (i == -1) {
			return RespBean.error("成绩重复，添加失败!");
		}
		return RespBean.error("添加成绩失败!");
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
