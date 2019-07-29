package com.ziwu.service;

import java.util.List;

import com.ziwu.pojo.Grade;
import com.ziwu.pojo.GradeAllocation;
import com.ziwu.pojo.GradeType;
import com.ziwu.pojo.RegularGrade;

public interface GradeService {
	// 
	List<GradeType> getAllGradeType();
	
	// 
	int updateAllocationByTeacherCourseId(Integer teacher_course_id,float[] allocations);
	
	// 
	List<GradeAllocation> getAllocationByTeacherCourseId(Integer teacher_course_id);
	
	// 
	int addRegularGrade(Integer user_course_id,Integer number,float grade,Integer type_id);
	
	// 
	List<RegularGrade> getRegularGrade(Integer teacher_course_id);
	
	// 删除
	boolean deleteGrade(String ids);
	
	// 
	int updateGrade(RegularGrade regularGrade);
	
	// 
	List<RegularGrade> getRegularGradeById(Integer user_course_id);
	
	// 
	List<Grade> getFianlGradeById(Integer user_course_id);
	
	// 
	int addFinalGrade(Integer user_course_id,float final_grade);
	
	// 
	List<Grade> getTotalGradeByTeacherCourseId(Integer teacher_course_id);
	
	// 
	int updateTotalGrade(Grade grade);
	
	// 
	List<RegularGrade> getAverageGrade(Integer user_course_id);
}
