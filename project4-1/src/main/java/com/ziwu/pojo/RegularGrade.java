package com.ziwu.pojo;

public class RegularGrade {
	private int id;
	private int user_course_id;
	private int number;
	private float grade;
	private int type_id;
	private User student;
	private GradeType gradeType;
	
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public float getGrade() {
		return grade;
	}
	public void setGrade(float grade) {
		this.grade = grade;
	}
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public User getStudent() {
		return student;
	}
	public void setStudent(User student) {
		this.student = student;
	}
	public GradeType getGradeType() {
		return gradeType;
	}
	public void setGradeType(GradeType gradeType) {
		this.gradeType = gradeType;
	}
}
