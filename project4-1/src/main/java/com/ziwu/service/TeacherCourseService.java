package com.ziwu.service;

import java.util.List;

import com.ziwu.pojo.TeacherCourse;

public interface TeacherCourseService {
	// (所有)分页查找教师课程信息
	List<TeacherCourse> getTeacherCourseByPage(Integer page, Integer size, String keywords);

	// (所有)根据关键字获取数目
	Long getCountByKeywords(String keywords);
	
	// 根据老师id查找课程
	List<TeacherCourse> getTeacherCourseById(Integer id);
	
	// 删除分配教师课程
	boolean deleteTeacherCourse(String ids);
	
	// 给教师分配课程
	int insertTeacherCourse(Integer[] user_ids, Integer[] course_ids);
	
	// 查找所有教师课程
	List<TeacherCourse> getAllTeacherCourse();
}
