package com.ziwu.pojo;

public class Course {
	private int id;
	private String course_id;
	private String name;
	private int isExam;
	private float proportion;
	private String introduction;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsExam() {
		return isExam;
	}
	public void setIsExam(int isExam) {
		this.isExam = isExam;
	}
	public float getProportion() {
		return proportion;
	}
	public void setProportion(float proportion) {
		this.proportion = proportion;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
