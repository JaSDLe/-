package com.ziwu.service;

import java.util.List;

import com.ziwu.pojo.Role;

public interface RoleService {
	//
	List<Role> roles();
	
	//
	int addNewRole(String name,String nameZh);
	
	//
	int deleteRoleById(int id);
}
