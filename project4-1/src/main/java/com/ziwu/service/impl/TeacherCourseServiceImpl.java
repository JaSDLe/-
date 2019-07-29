package com.ziwu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ziwu.mapper.TeacherCourseMapper;
import com.ziwu.pojo.TeacherCourse;
import com.ziwu.service.TeacherCourseService;

@Service
@Transactional
public class TeacherCourseServiceImpl implements TeacherCourseService{

	@Autowired
	private TeacherCourseMapper teacherCourseMapper;
	
	@Override
	public List<TeacherCourse> getTeacherCourseByPage(Integer page, Integer size, String keywords) {
		int start = (page - 1) * size;
		System.out.println("getTeacherCourseByPage start:"+start);
		return teacherCourseMapper.getTeacherCourseByPage(start, size, keywords);
	}

	@Override
	public Long getCountByKeywords(String keywords) {
		System.out.println("getCountByKeywords keywords:"+keywords);
		Long count = teacherCourseMapper.getCountByKeywords(keywords);
		System.out.println("getCountByKeywords count:"+count);
		return count;
	}

	@Override
	public List<TeacherCourse> getTeacherCourseById(Integer id) {
		return teacherCourseMapper.getTeacherCourseById(id);
	}

	@Override
	public boolean deleteTeacherCourse(String ids) {
		String[] split = ids.split(",");
		return teacherCourseMapper.deleteTeacherCourse(split) == split.length;
	}

	@Override
	public int insertTeacherCourse(Integer[] user_ids, Integer[] course_ids) {
		// 如果教师课程存在，返回错误
		for (Integer user_id : user_ids) {
			for (Integer course_id : course_ids) {
				if (teacherCourseMapper.getCountById(user_id, course_id) != 0) {
					return -1;
				}
			}
		}
		return teacherCourseMapper.addTeacherCourse(user_ids, course_ids);
	}

	@Override
	public List<TeacherCourse> getAllTeacherCourse() {
		return teacherCourseMapper.getAllTeacherCourse();
	}
}
