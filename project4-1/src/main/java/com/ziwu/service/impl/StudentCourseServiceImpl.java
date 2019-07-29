package com.ziwu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ziwu.mapper.StudentCourseMapper;
import com.ziwu.pojo.StudentCourse;
import com.ziwu.service.StudentCourseService;

@Service
@Transactional
public class StudentCourseServiceImpl implements StudentCourseService {

	@Autowired
	private StudentCourseMapper studentcourseMapper;

	@Override
	public List<StudentCourse> getStudentCourseByPage(Integer page, Integer size, String keywords) {
		int start = (page - 1) * size;
		System.out.println("start" + start);
		return studentcourseMapper.getStudentCourseByPage(start, size, keywords);
	}

	@Override
	public Long getCountByKeywords(String keywords) {
		System.out.println("keyword" + keywords);
		return studentcourseMapper.getCountByKeywords(keywords);
	}

	@Override
	public List<StudentCourse> getStudentCourseById(Integer id) {
		return studentcourseMapper.getStudentCourseById(id);
	}

	@Override
	public boolean deleteStudentCourse(String ids) {
		String[] split = ids.split(",");
		return studentcourseMapper.deleteStudentCourse(split) == split.length;
	}

	@Override
	public int insertStudentCourse(Integer[] user_ids, Integer[] teacher_course_ids) {
		// 如果学生课程存在，返回错误
		for (Integer user_id : user_ids) {
			for (Integer teacher_course_id : teacher_course_ids) {
				if (studentcourseMapper.getCountById(user_id, teacher_course_id) != 0) {
					return -1;
				}
			}
		}
		return studentcourseMapper.addStudentCourse(user_ids, teacher_course_ids);
	}
	
	@Override
	public List<StudentCourse> getStudentByTeacherCourseId(Integer id) {
		return studentcourseMapper.getStudentByTeacherCourseId(id);
	}
}
