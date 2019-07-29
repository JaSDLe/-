package com.ziwu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ziwu.pojo.RespBean;

@RestController
public class LoginController {

	@RequestMapping("/login_p")
	public RespBean login() {
		return RespBean.error("尚未登录，请登录!");
	}
}
