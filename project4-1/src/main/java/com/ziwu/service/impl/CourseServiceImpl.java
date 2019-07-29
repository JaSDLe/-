package com.ziwu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ziwu.mapper.CourseMapper;
import com.ziwu.pojo.Course;
import com.ziwu.service.CourseService;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseMapper courseMapper;

	// **************************************
	// 基本操作
	// **************************************
	
//	@Override
//	public Course findCourseByName(String name) {
//		return this.courseMapper.findCourseByName(name);
//	}
//	
//	@Override
//	public Course getCourseByCourse_id(String course_id) {
//		return courseMapper.getCourseByCourse_Id(course_id);
//	}

	@Override
	public int insertCourse(Course course) {
		//如果课程id存在，返回错误
		if(courseMapper.getCourseByCourse_Id(course.getCourse_id()) != null) {
			return -1;
		}
		return this.courseMapper.insertCourse(course);
	}

	@Override
	public boolean deleteCourse(String ids) {
		String[] split = ids.split(",");
		return courseMapper.deleteCourse(split) == split.length;
		
	}

	@Override
	public int updateCourse(Course course) {
		return this.courseMapper.updateCourse(course);
	}
	
	// **************************************
	// 分页查询
	// **************************************
	
	@Override
	public List<Course> getCourseByPage(Integer page, Integer size, String keywords) {
		int start = (page - 1) * size;
		return courseMapper.getCourseByPage(start, size, keywords);
	}

	@Override
	public Long getCountByKeywords(String keywords) {
		return courseMapper.getCountByKeywords(keywords);
	}

	// **************************************
	// 
	// **************************************
	
	@Override
	public List<Course> getAllCourse() {
		return courseMapper.getAllCourse();
	}
}
