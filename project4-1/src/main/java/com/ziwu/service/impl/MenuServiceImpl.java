package com.ziwu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ziwu.common.UserUtils;
import com.ziwu.mapper.MenuMapper;
import com.ziwu.pojo.Menu;
import com.ziwu.service.MenuService;

@Service
@Transactional
@CacheConfig(cacheNames = "menus_cache")
public class MenuServiceImpl implements MenuService{

	@Autowired
	MenuMapper menuMapper;

	@Override
	public List<Menu> getAllMenu() {
		return menuMapper.getAllMenu();
	}

	@Override
	public List<Menu> getMenusByUserId() {
		return menuMapper.getMenusByUserId(UserUtils.getCurrentUser().getId());
	}

	@Override
	public List<Menu> menuTree() {
		return menuMapper.menuTree();
	}

	@Override
	public List<Integer> getMenusByRoleId(int role_id) {
		return menuMapper.getMenusByRoleId(role_id);
	}
	
	
}
