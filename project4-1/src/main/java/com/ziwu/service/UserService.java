package com.ziwu.service;

import java.util.List;

import com.ziwu.pojo.Role;
import com.ziwu.pojo.User;

public interface UserService {
	// **************************************
	// 基本操作
	// **************************************
	
	// 根据用户名查找用户
	User findUserByUsername(String username);
	
	// 根据id查询用户信息
	User getUserById(int id);

	// 增加用户
	int insertUser(User user);
	
	// 删除用户
	boolean deleteUser(String ids);
		
	// 更新用户信息
	int updateUser(User user);
	
	// 验证密码
	int checkPassword(User user);

	// **************************************
	// 分页查询
	// **************************************
    
    // (所有)分页查找用户
    List<User> getUserByPage(Integer page, Integer size, String keywords);
    
    // (所有)根据关键字获取数目
    Long getCountByKeywords(String keywords);
    
    // (学生老师)分页查找用户
    List<User> getUserByOffPage(Integer page, Integer size, String keywords);
    
    // (学生老师)根据关键字获取数目
    Long getCountByOffKeywords(String keywords);
    
	// **************************************
	// 角色相关
	// **************************************
    
	// 为用户更新角色
	int updateUserRoles(int user_id, int[] role_ids);

	// 根据用户id查询角色
	List<Role> getRolesByUserId(int id);
	
	// **************************************
	// 
	// **************************************
	
	// 查找所有老师
	List<User> getAllTeacher();
	
	// 查找所有学生
	List<User> getAllStudent();
	// **************************************
	// 暂时没用
	// **************************************
	
//    // 根据关键字查找用户
//    List<User> getUsersByKeywords(String keywords);
	
//	// 查找除了管理员之外的所有用户
//	List<User> getAllUserExceptAdmin();
	
//	// 查找所有用户
//	List<User> getAllUser();
}
