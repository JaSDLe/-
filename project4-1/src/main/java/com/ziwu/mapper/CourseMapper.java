package com.ziwu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwu.pojo.Course;

public interface CourseMapper {
	// **************************************
	// 基本操作
	// **************************************
	
//	// 根据课程名查找课程
//	Course findCourseByName(String name);

	// 根据课程id查询课程信息
	Course getCourseByCourse_Id(String course_id);

	// 添加课程
	int insertCourse(Course course);

	// 删除课程
	int deleteCourse(@Param("ids") String[] ids);

	// 更新课程信息
	int updateCourse(Course course);

	// **************************************
	// 分页查询
	// **************************************
	
	// 分页查找课程
	List<Course> getCourseByPage(@Param("start") Integer start, @Param("size") Integer size,
			@Param("keywords") String keywords);

	// 根据关键字获取数目
	Long getCountByKeywords(@Param("keywords") String keywords);
	
	// **************************************
	// 
	// **************************************
	
	// 查找所有课程
	List<Course> getAllCourse();
}
