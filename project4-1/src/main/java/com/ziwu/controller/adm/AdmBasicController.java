package com.ziwu.controller.adm;

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
@RequestMapping("/admin/basic")
public class AdmBasicController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public Map<String, Object> getUserByPage(
    		@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String keywords) {
		Map<String, Object> map = new HashMap<>();
		List<User> userByPage = userService.getUserByPage(page, size, keywords);
		Long count = userService.getCountByKeywords(keywords);
		
//		System.out.println(userByPage.size());
//		List<User> off =new ArrayList<User>();
//		count = 0l;
//		for (int i = 0; i < userByPage.size(); i++) {
//			System.out.println(userByPage.get(i).getRoles().get(0).getNameZh());
//			if (userByPage.get(i).getRoles().get(0).getId() > 2) {
//				off.add(userByPage.get(i));
//			}
//		}off
		
		map.put("users", userByPage);
		map.put("count", count);
		return map;
	}
	
//	@RequestMapping("/{keywords}")
//	public List<User> getUsersByKeywords(@PathVariable(required = false) String keywords) {
//		List<User> users = userService.getUsersByKeywords(keywords);
//		return users;
//	}
	
	@RequestMapping(value = "/user/reg", method = RequestMethod.POST)
	public RespBean UserReg(User user,@RequestParam(required = true) Integer role_id) {
		System.out.println(role_id);
		int[] role_ids = new int[1];
		if (role_id == null) {
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
		System.out.println(ids);
		if (userService.deleteUser(ids)) {
			return RespBean.ok("删除成功!");
		}
		return RespBean.error("删除失败!");
	}
	
//	@InitBinder
//	protected void init(WebDataBinder webDataBinder) {
//		webDataBinder.registerCustomEditor(Role.class, "myRole", new PropertyEditor(,true));
//	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public RespBean updateUser(User user,@RequestParam(required = true) Integer role_id) {
		System.out.println(role_id);
		System.out.println(user.getId());
		System.out.println(user.getName());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
//		System.out.println(user.getRoles().toString());
		if ((userService.updateUser(user) == 1) && (userService.updateUserRoles(user.getId(), new int[]{role_id}) == 1)) {
			System.out.println("success");
			return RespBean.ok("更新成功!");
		}
		System.out.println("fail");
		return RespBean.error("更新失败!");
	}
	
	@RequestMapping(value = "/roles", method = RequestMethod.PUT)
	public RespBean updateUserRoles(int user_id,int[] role_ids) {
		if (userService.updateUserRoles(user_id, role_ids) == role_ids.length) {
			return RespBean.ok("更新成功!");
		}
		return RespBean.error("更新失败!");
	}
}
