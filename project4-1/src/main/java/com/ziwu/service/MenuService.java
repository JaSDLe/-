package com.ziwu.service;

import java.util.List;

import com.ziwu.pojo.Menu;

public interface MenuService {
	//
	List<Menu> getAllMenu();
	
	//
	List<Menu> getMenusByUserId();
	
	//
	List<Menu> menuTree();
	
	//
	List<Integer> getMenusByRoleId(int role_id);
}
