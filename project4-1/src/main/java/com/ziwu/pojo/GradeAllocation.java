package com.ziwu.pojo;

public class GradeAllocation {
	private int id;
	private int user_course_id;
	private int type_id;
	private float allocation;
	
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
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public float getAllocation() {
		return allocation;
	}
	public void setAllocation(float allocation) {
		this.allocation = allocation;
	}
}
