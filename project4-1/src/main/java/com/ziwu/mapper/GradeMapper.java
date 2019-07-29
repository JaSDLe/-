package com.ziwu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwu.pojo.Grade;
import com.ziwu.pojo.GradeAllocation;
import com.ziwu.pojo.GradeType;
import com.ziwu.pojo.RegularGrade;

public interface GradeMapper {
	// 
	List<GradeType> getAllGradeType();
	
	// 
	int addAllocation(Integer teacher_course_id,Integer type_id,float allocation);
	
	// 
	int deleteAllocationByTeacherCourseId(Integer teacher_course_id);
	
	// 
	List<GradeAllocation> getAllocationByTeacherCourseId(Integer teacher_course_id);
	
	// 
	int addRegularGrade(Integer user_course_id,Integer number,float grade,Integer type_id);
	
	// 
	int findGrade(Integer user_course_id,Integer number,Integer type_id);
	
	// 
	List<RegularGrade> getRegularGrade(Integer teacher_course_id);
	
	// 删除
	int deleteGrade(@Param("ids") String[] ids);
	
	// 
	int updateGrade(RegularGrade regularGrade);
	
	// 
	List<RegularGrade> getRegularGradeById(Integer user_course_id);
	
	// 
	List<Grade> getFianlGradeById(Integer user_course_id);
	
	// 
	int addFinalGrade(Integer user_course_id,float final_grade);
	
	// 
	int findFinalGrade(Integer user_course_id);
	
	// 
	List<Grade> getTotalGradeByTeacherCourseId(Integer teacher_course_id);
	
	// 
	int updateTotalGrade(Grade grade);
	
	// 
	float getAverageGrade(Integer user_course_id,Integer type_id,Integer number);
}
