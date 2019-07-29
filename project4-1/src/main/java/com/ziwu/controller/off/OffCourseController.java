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

import com.ziwu.pojo.Course;
import com.ziwu.pojo.RespBean;
import com.ziwu.service.CourseService;

@RestController
@RequestMapping("/office/course")
public class OffCourseController {

	@Autowired
	CourseService courseService;
	
	@RequestMapping(value = "/course", method = RequestMethod.GET)
    public Map<String, Object> getCourseByPage(
    		@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String keywords) {
		Map<String, Object> map = new HashMap<>();
		List<Course> courseByPage = courseService.getCourseByPage(page, size, keywords);
		Long count =courseService.getCountByKeywords(keywords);
		map.put("courses", courseByPage);
		map.put("count", count);
		return map;
	}
	
	@RequestMapping(value = "/course/ins", method = RequestMethod.POST)
	public RespBean CourseIns(Course course) {		
		int i=courseService.insertCourse(course);
		if (i == 1) {
			return RespBean.ok("添加成功!");
		} else if (i == -1) {
			return RespBean.error("课程代码重复，添加失败!");
		}
		return RespBean.error("添加失败!");
	}
	
	@RequestMapping(value = "/course/{ids}", method = RequestMethod.DELETE)
	public RespBean deleteCourse(@PathVariable String ids) {
		if (courseService.deleteCourse(ids)) {
			return RespBean.ok("删除成功!");
		}
		return RespBean.error("删除失败!");
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public RespBean updateCourse(Course course) {
		if (courseService.updateCourse(course) == 1) {
			return RespBean.ok("更新成功!");
		}
		return RespBean.error("更新失败!");
	}
}
