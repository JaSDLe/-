package com.ziwu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ziwu.mapper.GradeMapper;
import com.ziwu.pojo.Grade;
import com.ziwu.pojo.GradeAllocation;
import com.ziwu.pojo.GradeType;
import com.ziwu.pojo.RegularGrade;
import com.ziwu.service.GradeService;

@Service
@Transactional
public class GradeServiceImpl implements GradeService {

	@Autowired
	GradeMapper gradeMapper;
	
	@Override
	public List<GradeType> getAllGradeType() {
		return gradeMapper.getAllGradeType();
	}

	@Override
	public int updateAllocationByTeacherCourseId(Integer teacher_course_id, float[] allocations) {
		gradeMapper.deleteAllocationByTeacherCourseId(teacher_course_id);
		for (int i = 0; i < allocations.length; i++) {
			if (allocations[i] != 0) {
				gradeMapper.addAllocation(teacher_course_id, (i+1), allocations[i]);
			}
		}
		return 1;
	}

	@Override
	public List<GradeAllocation> getAllocationByTeacherCourseId(Integer teacher_course_id) {
		return gradeMapper.getAllocationByTeacherCourseId(teacher_course_id);
	}

	@Override
	public int addRegularGrade(Integer user_course_id, Integer number, float grade, Integer type_id) {
		if (gradeMapper.findGrade(user_course_id, number, type_id) != 0) {
			System.out.println("grade not null");
			return -1;
		}
		System.out.println("grade null");
		return gradeMapper.addRegularGrade(user_course_id, number, grade, type_id);
	}

	@Override
	public List<RegularGrade> getRegularGrade(Integer teacher_course_id) {
		return gradeMapper.getRegularGrade(teacher_course_id);
	}

	@Override
	public boolean deleteGrade(String ids) {
		String[] split = ids.split(",");
		return gradeMapper.deleteGrade(split) == split.length;
	}

	@Override
	public int updateGrade(RegularGrade regularGrade) {
		return gradeMapper.updateGrade(regularGrade);
	}

	@Override
	public List<RegularGrade> getRegularGradeById(Integer user_course_id) {
		return gradeMapper.getRegularGradeById(user_course_id);
	}

	@Override
	public List<Grade> getFianlGradeById(Integer user_course_id) {
		return gradeMapper.getFianlGradeById(user_course_id);
	}

	@Override
	public int addFinalGrade(Integer user_course_id, float final_grade) {
		if (gradeMapper.findFinalGrade(user_course_id) != 0) {
			System.out.println("final_grade not null");
			return -1;
		}
		System.out.println("final_grade null");
		return gradeMapper.addFinalGrade(user_course_id, final_grade);
	}

	@Override
	public List<Grade> getTotalGradeByTeacherCourseId(Integer teacher_course_id) {
		return gradeMapper.getTotalGradeByTeacherCourseId(teacher_course_id);
	}

	@Override
	public int updateTotalGrade(Grade grade) {
		return gradeMapper.updateTotalGrade(grade);
	}

	@Override
	public List<RegularGrade> getAverageGrade(Integer user_course_id) {
		List<RegularGrade> regularGrades = gradeMapper.getRegularGradeById(user_course_id);
		for (int i = 0; i < regularGrades.size(); i++) {
			RegularGrade regularGrade = regularGrades.get(i);
			float avg = gradeMapper.getAverageGrade(user_course_id,regularGrade.getType_id(),regularGrade.getNumber());
			regularGrades.get(i).setGrade(avg);
		}
		return regularGrades;
	}

}
