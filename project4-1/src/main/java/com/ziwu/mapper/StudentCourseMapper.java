package com.ziwu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwu.pojo.StudentCourse;

public interface StudentCourseMapper {
	
	// 所有分页查找分配学生课程
	List<StudentCourse> getStudentCourseByPage(@Param("start") Integer start, @Param("size") Integer size,
			@Param("keywords") String keywords);
	
	// 所有根据关键字获取数目
	Long getCountByKeywords(@Param("keywords") String keywords);
	
	// 根据User_id查找分配学生课程
	List<StudentCourse> getStudentCourseById(@Param("id") Integer id);
	
	// 删除分配学生课程
	int deleteStudentCourse(@Param("ids") String[] ids);
	
	// 根据两个id获取数目
	Long getCountById(@Param("user_id") Integer user_id,@Param("teacher_course_id") Integer teacher_course_id);
		
	// 分配学生课程
	int addStudentCourse(@Param("user_ids") Integer[] user_ids, @Param("teacher_course_ids") Integer[] teacher_course_ids);
	
	// 查找所有选课的学生
	List<StudentCourse> getStudentByTeacherCourseId(@Param("id") Integer id);
//	// 增加分配学生课程
//	int insertStudentCourse(StudentCourse studentcourse);
	
//	// 更新分配学生课程
//	int updateStudentCourse(StudentCourse studentcourse);
	
	// 根据学生课程id查询学生
	//List<User>getUsersByStudentCourseId(int id);
	
	// 根据6项之中 1或者2 增删查
	
//	StudentCourse findStudentCourseByUser_IdAndTeacher_Course_Id(int user_id,int teacher_course_id);
}
