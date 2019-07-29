package com.ziwu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ziwu.mapper.UserMapper;
import com.ziwu.pojo.Role;
import com.ziwu.pojo.User;
import com.ziwu.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	// **************************************
	// 基本操作
	// **************************************

	@Override
	public User findUserByUsername(String username) {
		return this.userMapper.findUserByUsername(username);
	}

	@Override
	public User getUserById(int id) {
		return userMapper.getUserById(id);
	}

	@Override
	public int insertUser(User user) {
		// 如果用户名存在，返回错误
		if (userMapper.findUserByUsername(user.getUsername()) != null) {
			return -1;
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		return this.userMapper.insertUser(user);
	}

	@Override
	public int updateUser(User user) {
		System.out.println("updateUser");
		// || user.getPassword().equals(null) || user.getPassword().equals("")
		if (user.getPassword() == null) {
			System.out.println("password null");
			user.setPassword(null);
		} else {
			System.out.println("password not null");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
		}
		return this.userMapper.updateUser(user);
	}

	@Override
	public boolean deleteUser(String ids) {
		String[] split = ids.split(",");
		for (int i = 0; i < split.length; i++) {
			int user_id = Integer.valueOf(split[i]);
			this.updateUserRoles(user_id, null);
		}
		return userMapper.deleteUser(split) == split.length;
	}

	@Override
	public int checkPassword(User user) {
		User oldUser = userMapper.getUserById(user.getId());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (encoder.matches(user.getPassword(), oldUser.getPassword())) {
			return 1;
		} else {
			return 0;
		}
	}

	// **************************************
	// 分页查询
	// **************************************

	@Override
	public List<User> getUserByPage(Integer page, Integer size, String keywords) {
		int start = (page - 1) * size;
		return userMapper.getUserByPage(start, size, keywords);
	}

	@Override
	public Long getCountByKeywords(String keywords) {
		return userMapper.getCountByKeywords(keywords);
	}

	@Override
	public List<User> getUserByOffPage(Integer page, Integer size, String keywords) {
		int start = (page - 1) * size;
		return userMapper.getUserByOffPage(start, size, keywords);
	}

	@Override
	public Long getCountByOffKeywords(String keywords) {
		return userMapper.getCountByOffKeywords(keywords);
	}

	// **************************************
	// 角色相关
	// **************************************

	@Override
	public List<Role> getRolesByUserId(int id) {
		return userMapper.getRolesByUserId(id);
	}

	@Override
	public int updateUserRoles(int user_id, int[] role_ids) {
		userMapper.deleteRoleByUserId(user_id);
		if (role_ids != null) {
			return userMapper.addRolesForUser(user_id, role_ids);
		} else {
			return 0;
		}
	}

	// **************************************
	// 
	// **************************************
	
	@Override
	public List<User> getAllTeacher() {
		return userMapper.getAllTeacher();
	}

	@Override
	public List<User> getAllStudent() {
		return userMapper.getAllStudent();
	}
	
	// **************************************
	// 暂时没用
	// **************************************

//	@Override
//	public List<User> getUsersByKeywords(String keywords) {
//		return userMapper.getUsersByKeywords(keywords);
//	}

//	@Override
//	public List<User> getAllUserExceptAdmin() {
////		return userMapper.getAllUser(UserUtils.getCurrentUser().getId());
//		return userMapper.getAllUser();
//	}

//	@Override
//	public List<User> getAllUser() {
//		return userMapper.getAllUser();
//	}
}
