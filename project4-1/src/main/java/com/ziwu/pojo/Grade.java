package com.ziwu.pojo;

public class Grade {
	private int id;
	private int user_course_id;
	private float regular_grade;
	private float final_grade;
	private float total_grade;
	private User student;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_course_id() {
		return user_course_id;
	}
	public void setUser_course_id(int user_course_id) {
		this.user_course_id = user_course_id;
	}
	public float getRegular_grade() {
		return regular_grade;
	}
	public void setRegular_grade(float regular_grade) {
		this.regular_grade = regular_grade;
	}
	public float getFinal_grade() {
		return final_grade;
	}
	public void setFinal_grade(float final_grade) {
		this.final_grade = final_grade;
	}
	public float getTotal_grade() {
		return total_grade;
	}
	public void setTotal_grade(float total_grade) {
		this.total_grade = total_grade;
	}
	public User getStudent() {
		return student;
	}
	public void setStudent(User student) {
		this.student = student;
	}
}
