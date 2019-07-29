package com.ziwu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwu.pojo.Role;

public interface RoleMapper {
	//
	List<Role> roles();
	
	//
	int addNewRole(@Param("name") String name,@Param("nameZh") String nameZh);
	
	//
	int deleteRoleById(int id);
}
