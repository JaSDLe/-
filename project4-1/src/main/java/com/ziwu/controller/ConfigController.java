package com.ziwu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ziwu.common.UserUtils;
import com.ziwu.pojo.Course;
import com.ziwu.pojo.GradeType;
import com.ziwu.pojo.Menu;
import com.ziwu.pojo.RespBean;
import com.ziwu.pojo.User;
import com.ziwu.service.CourseService;
import com.ziwu.service.GradeService;
import com.ziwu.service.MenuService;
import com.ziwu.service.UserService;

@RestController
@RequestMapping("/config")
public class ConfigController {

	@Autowired
	MenuService menuService;

	@Autowired
	UserService userService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	GradeService gradeService;

	@RequestMapping("/sysmenu")
	public List<Menu> sysmenu() {
		return menuService.getMenusByUserId();
	}

	@RequestMapping("/user")
	public User currentUser() {
		return UserUtils.getCurrentUser();
	}

	@RequestMapping(value = "/change/password", method = RequestMethod.PUT)
	public RespBean changePassword(
			@RequestParam(required = true) Integer id,
            @RequestParam(required = true) String oldPass,
            @RequestParam(required = true) String newPass) {
		System.out.println(id);
		System.out.println(oldPass);
		System.out.println(newPass);
		User user = userService.getUserById(id);
		user.setPassword(oldPass);
		if (userService.checkPassword(user) == 1) {
			user.setPassword(newPass);
			if (userService.updateUser(user) == 1) {
				return RespBean.ok("密码修改成功!");
			}
			return RespBean.error("密码修改失败!");
		} else {
			return RespBean.error("原密码错误!");
		}
	}
	
	@RequestMapping("/course")
	public List<Course> getAllCourse() {
		return courseService.getAllCourse();
	}
	
	@RequestMapping("/gradeType")
	public List<GradeType> getAllGradeType() {
		return gradeService.getAllGradeType();
	}
}
