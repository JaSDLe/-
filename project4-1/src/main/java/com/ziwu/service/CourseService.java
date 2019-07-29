package com.ziwu.service;

import java.util.List;

import com.ziwu.pojo.Course;

public interface CourseService {
	// **************************************
	// 基本操作
	// **************************************
	
//	// 根据课程名查找课程
//	Course findCourseByName(String name);

//	// 根据课程id查询课程信息
//	Course getCourseByCourse_id(String course_id);

	// 添加课程
	int insertCourse(Course course);

	// 删除课程
	boolean deleteCourse(String ids);

	// 更新课程信息
	int updateCourse(Course course);
	
	// **************************************
	// 分页查询
	// **************************************

	// 分页查找课程
	List<Course> getCourseByPage(Integer page, Integer size, String keywords);

	// 根据关键字获取数目
	Long getCountByKeywords(String keywords);
	
	// **************************************
	// 
	// **************************************
	
	// 查找所有课程
	List<Course> getAllCourse();
}
