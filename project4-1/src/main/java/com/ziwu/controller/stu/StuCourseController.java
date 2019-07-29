package com.ziwu.controller.stu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ziwu.pojo.Grade;
import com.ziwu.pojo.GradeAllocation;
import com.ziwu.pojo.RegularGrade;
import com.ziwu.pojo.StudentCourse;
import com.ziwu.service.GradeService;
import com.ziwu.service.StudentCourseService;

@RestController
@RequestMapping("/student/course")
public class StuCourseController {
	
	@Autowired
	StudentCourseService studentCourseService;
	
	@Autowired
	GradeService gradeService;
	
	@RequestMapping(value = "/course", method = RequestMethod.GET)
    public List<StudentCourse> getStudentCourseById(@RequestParam(required = true) Integer id) {
		return studentCourseService.getStudentCourseById(id);
	}
	
	@RequestMapping(value = "/grade", method = RequestMethod.GET)
    public List<RegularGrade> getRegularGradeById(@RequestParam(required = true) Integer user_course_id) {
		return gradeService.getRegularGradeById(user_course_id);
	}
	
	@RequestMapping(value = "/allgrade", method = RequestMethod.GET)
	public List<Grade> getFianlGradeById(@RequestParam(required = true) Integer user_course_id){
		return gradeService.getFianlGradeById(user_course_id);
	}
	
	@RequestMapping(value = "/getAverageGrade", method = RequestMethod.GET)
    public List<RegularGrade> getAverageGrade(@RequestParam(required = true) Integer user_course_id) {
		return gradeService.getAverageGrade(user_course_id);
	}
	
	@RequestMapping(value = "/getAllocation", method = RequestMethod.GET)
	public List<GradeAllocation> getAllocationByTeacherCourseId(
			@RequestParam(required = true) Integer teacher_course_id) {
		return gradeService.getAllocationByTeacherCourseId(teacher_course_id);
	}
}
