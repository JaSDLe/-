package com.ziwu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwu.pojo.Role;
import com.ziwu.pojo.User;

public interface UserMapper {
	// **************************************
	// 基本操作
	// **************************************
	
	// 根据用户名查找用户
	User findUserByUsername(String username);
	
	// 根据id查询用户信息
	User getUserById(int id);

	// 增加用户（创建）
	int insertUser(User user);

	// 删除用户
	int deleteUser(@Param("ids") String[] ids);

	// 更新用户信息（修改）
	int updateUser(User user);

	// **************************************
	// 分页查询
	// **************************************
	
	// (所有)分页查找用户
	List<User> getUserByPage(@Param("start") Integer start, @Param("size") Integer size,
			@Param("keywords") String keywords);

	// (所有)根据关键字获取数目
	Long getCountByKeywords(@Param("keywords") String keywords);

	// (学生老师)分页查找用户
	List<User> getUserByOffPage(@Param("start") Integer start, @Param("size") Integer size,
			@Param("keywords") String keywords);

	// (学生老师)根据关键字获取数目
	Long getCountByOffKeywords(@Param("keywords") String keywords);

	// **************************************
	// 角色相关
	// **************************************
	
	// 根据用户id查询角色
	List<Role> getRolesByUserId(int id);

	// 为用户增加角色
	int addRolesForUser(@Param("user_id") int user_id, @Param("role_ids") int[] role_ids);

	// 根据用户id删除角色
	int deleteRoleByUserId(int user_id);
	
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
	
	// 根据关键字查找用户
	List<User> getUsersByKeywords(@Param("keywords") String keywords);
	
	// 查找所有用户
// 	List<User> getAllUser(@Param("currentId") int currentId);
	List<User> getAllUser();
}
