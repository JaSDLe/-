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

import com.ziwu.pojo.RespBean;
import com.ziwu.pojo.StudentCourse;
import com.ziwu.service.StudentCourseService;

@RestController
@RequestMapping("/studentcourse")
public class OffStudentCourseController {

	@Autowired
	StudentCourseService studentcourseService;

	@RequestMapping(value = "/sc", method = RequestMethod.GET)
	public Map<String, Object> getStudentCourseByPage(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "") String keywords) {
		Map<String, Object> map = new HashMap<>();
		Long count = studentcourseService.getCountByKeywords(keywords);
		System.out.println("count" + count);
		List<StudentCourse> studentcourseByPage = studentcourseService.getStudentCourseByPage(page, size, keywords);
		map.put("studentcourses", studentcourseByPage);
		map.put("count", count);
		return map;
	}

	@RequestMapping(value = "/sc/{ids}", method = RequestMethod.DELETE)
	public RespBean deleteStudentCourse(@PathVariable String ids) {
		if (studentcourseService.deleteStudentCourse(ids)) {
			return RespBean.ok("删除成功!");
		}
		return RespBean.error("删除失败!");
	}

	@RequestMapping(value = "/ins", method = RequestMethod.POST)
	public RespBean insStudentCourse(Integer[] user_ids,Integer[] teacher_course_ids) {
		int i=studentcourseService.insertStudentCourse(user_ids, teacher_course_ids);
		if (i == user_ids.length * teacher_course_ids.length) {
			return RespBean.ok("课程分配成功!");
		} else if (i == -1) {
			return RespBean.error("课程分配重复，分配失败!");
		}
		return RespBean.error("分配失败!");
	}
}
