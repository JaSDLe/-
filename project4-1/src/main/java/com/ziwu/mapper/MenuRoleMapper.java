package com.ziwu.mapper;

import org.apache.ibatis.annotations.Param;

public interface MenuRoleMapper {
	//
	int deleteMenuByRoleId(@Param("role_id") int role_id);
	
	//
	int addMenu(@Param("role_id") int role_id,@Param("menu_ids") int[] menu_ids);
}
