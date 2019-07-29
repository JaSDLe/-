package com.ziwu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwu.pojo.TeacherCourse;

public interface TeacherCourseMapper {

	// (所有)分页查找教师课程
	List<TeacherCourse> getTeacherCourseByPage(@Param("start") Integer start, @Param("size") Integer size,
			@Param("keywords") String keywords);

	// (所有)根据关键字获取数目
	Long getCountByKeywords(@Param("keywords") String keywords);
	
	// 根据老师id查找课程
	List<TeacherCourse> getTeacherCourseById(@Param("id") Integer id);
	
	// 删除分配教师课程
	int deleteTeacherCourse(@Param("ids") String[] ids);
	
	// 根据两个id获取数目
	Long getCountById(@Param("user_id") Integer user_id,@Param("course_id") Integer course_id);
	
	// 分配教师课程
	int addTeacherCourse(@Param("user_ids") Integer[] user_ids, @Param("course_ids") Integer[] course_ids);
	
	// 查找所有教师课程
	List<TeacherCourse> getAllTeacherCourse();
}
