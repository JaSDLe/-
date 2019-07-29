package com.ziwu.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.ziwu.pojo.User;

public class UserUtils {
	public static User getCurrentUser() {
		User user = new User();
		SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
        	user = (User) auth.getPrincipal();
        }
        return user;
//		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
