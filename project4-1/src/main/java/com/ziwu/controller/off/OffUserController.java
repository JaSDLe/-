package com.ziwu.controller.off;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ziwu.pojo.RespBean;
import com.ziwu.pojo.User;
import com.ziwu.service.UserService;

@RestController
@RequestMapping("/office/user")
public class OffUserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public Map<String, Object> getUserByPage(
    		@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String keywords) {
		Map<String, Object> map = new HashMap<>();
		List<User> userByPage = userService.getUserByOffPage(page, size, keywords);
		Long count = userService.getCountByOffKeywords(keywords);
		map.put("users", userByPage);
		map.put("count", count);
		return map;
	}
	
	@RequestMapping(value = "/user/reg", method = RequestMethod.POST)
	public RespBean UserReg(User user,@RequestParam(required = true) Integer role_id) {
		System.out.println(role_id);
		int[] role_ids = new int[1];
		if (role_id == null || role_id < 3) {
			role_ids[0] = 4;
		} else {
			role_ids[0] = role_id;
		}
		user.setPassword("cuit"+user.getUsername());
		int i = userService.insertUser(user);
		if (i == 1) {
			User newUser = userService.findUserByUsername(user.getUsername());
			userService.updateUserRoles(newUser.getId(), role_ids);
			return RespBean.ok("注册成功!");
		} else if (i == -1) {
			return RespBean.error("用户名重复，注册失败!");
		}
		return RespBean.error("注册失败!");
	}
	
	@RequestMapping(value = "/user/{ids}", method = RequestMethod.DELETE)
	public RespBean deleteUser(@PathVariable String ids) {
		if (userService.deleteUser(ids)) {
			return RespBean.ok("删除成功!");
		}
		return RespBean.error("删除失败!");
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public RespBean updateUser(User user,@RequestParam(required = true) Integer role_id) {
		if (role_id < 3) {
			role_id = 4;
		}
		if ((userService.updateUser(user) == 1) && (userService.updateUserRoles(user.getId(), new int[]{role_id}) == 1)) {
			return RespBean.ok("更新成功!");
		}
		return RespBean.error("更新失败!");
	}
	
	@RequestMapping(value = "/roles", method = RequestMethod.PUT)
	public RespBean updateUserRoles(int user_id,int[] role_ids) {
		if (userService.updateUserRoles(user_id, role_ids) == role_ids.length) {
			return RespBean.ok("更新成功!");
		}
		return RespBean.error("更新失败!");
	}
	
	@RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public List<User> getAllTeacher() {
		return userService.getAllTeacher();
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.GET)
    public List<User> getAllStudent() {
		return userService.getAllStudent();
	}
}
