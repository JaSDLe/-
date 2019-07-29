package com.ziwu.mapper;

import java.util.List;

import com.ziwu.pojo.Menu;

public interface MenuMapper {
	//
	List<Menu> getAllMenu();
	
	//
	List<Menu> getMenusByUserId(int user_id);
	
	//
	List<Menu> menuTree();
	
	//
	List<Integer> getMenusByRoleId(int role_id);
}
