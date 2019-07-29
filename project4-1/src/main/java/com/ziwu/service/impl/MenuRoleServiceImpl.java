package com.ziwu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ziwu.mapper.MenuRoleMapper;
import com.ziwu.service.MenuRoleService;

@Service
@Transactional
public class MenuRoleServiceImpl implements MenuRoleService{

	@Autowired
	MenuRoleMapper menuRoleMapper;
	
	@Override
	public int updateMenu(int role_id, int[] menu_ids) {
		menuRoleMapper.deleteMenuByRoleId(role_id);
		if (menu_ids.length == 0) {
			return 0;
		}
		return menuRoleMapper.addMenu(role_id, menu_ids);
	}

}
