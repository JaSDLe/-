package com.ziwu.service;

import java.util.List;

import com.ziwu.pojo.StudentCourse;

public interface StudentCourseService {
	// 所有分页查找分配学生课程
	List<StudentCourse> getStudentCourseByPage(Integer page, Integer size, String keywords);
	
	// 所有根据关键字获取数目
	Long getCountByKeywords(String keywords);

	// 根据学生课程id查询学生
	List<StudentCourse> getStudentCourseById(Integer id);
	
	// 删除分配学生课程
	boolean deleteStudentCourse(String ids);

	// 给教师分配课程
	int insertStudentCourse(Integer[] user_ids, Integer[] teacher_course_ids);
	
	// 查找所有选课的学生
	List<StudentCourse> getStudentByTeacherCourseId(Integer id);
//	// 更新分配学生课程
//	int updateStudentCourse(StudentCourse studentcourse);

	// 根据学生课程id查询学生
	// List<User>getUsersByStudentCourseId(int id);

//	// 根据课程代码查找分配学生课程
//	StudentCourse findStudentCourseByUser_IdAndTeacher_Course_Id(int user_id, int teacher_course_id);
	
//	// 增加分配学生课程
//	int insertStudentCourse(StudentCourse studentcourse);
}
