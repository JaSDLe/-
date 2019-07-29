package com.ziwu.pojo;

public class StudentCourse {
	private int id;
	private int user_id;
	private int teacher_course_id;
	private User student;
	private TeacherCourse teacherCourse;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getTeacher_course_id() {
		return teacher_course_id;
	}
	public void setTeacher_course_id(int teacher_course_id) {
		this.teacher_course_id = teacher_course_id;
	}
	public User getStudent() {
		return student;
	}
	public void setStudent(User student) {
		this.student = student;
	}
	public TeacherCourse getTeacherCourse() {
		return teacherCourse;
	}
	public void setTeacherCourse(TeacherCourse teacherCourse) {
		this.teacherCourse = teacherCourse;
	}
}
