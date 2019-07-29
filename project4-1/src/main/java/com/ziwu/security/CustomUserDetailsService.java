package com.ziwu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ziwu.pojo.User;
import com.ziwu.service.UserService;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在！");
		}
//		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		for (Role role : userService.getRolesByUserId(user.getId())) {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//			System.out.println(role.getName());
//		}
////		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//				authorities);
		return user;
	}
}
