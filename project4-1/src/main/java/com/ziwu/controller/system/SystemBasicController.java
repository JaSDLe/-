package com.ziwu.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ziwu.pojo.Menu;
import com.ziwu.pojo.RespBean;
import com.ziwu.pojo.Role;
import com.ziwu.service.MenuRoleService;
import com.ziwu.service.MenuService;
import com.ziwu.service.RoleService;

@RestController
@RequestMapping("/system/basic")
public class SystemBasicController {
	@Autowired
	RoleService roleService;
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	MenuRoleService menuRoleService;
	
	@RequestMapping(value = "/menuTree/{role_id}", method = RequestMethod.GET)
    public Map<String, Object> menuTree(@PathVariable int role_id) {
		Map<String, Object> map = new HashMap<>();
		List<Menu> menus = menuService.menuTree();
		map.put("menus", menus);
		List<Integer> mids = menuService.getMenusByRoleId(role_id);
		map.put("mids", mids);
		return map;
	}
	
	@RequestMapping("/roles")
    public List<Role> allRoles() {
		return roleService.roles();
	}
	
	@RequestMapping(value = "/role/{role_id}", method = RequestMethod.DELETE)
    public RespBean deleteRole(@PathVariable int role_id) {
		if (roleService.deleteRoleById(role_id) == 1) {
			return RespBean.ok("删除成功!");
		}
		return RespBean.error("删除失败!");
	}
	
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public RespBean addNewRole(String role, String roleZh) {
		if (roleService.addNewRole(role, roleZh) == 1) {
			return RespBean.ok("添加成功!");
		}
		return RespBean.error("添加失败!");
	}
	
	@RequestMapping(value = "/updateMenuRole", method = RequestMethod.PUT)
    public RespBean updateMenuRole(int role_id, int[] menu_ids) {
		if (menuRoleService.updateMenu(role_id, menu_ids) == menu_ids.length) {
			return RespBean.ok("更新成功!");
		}
		return RespBean.error("更新失败!");
	}
}
