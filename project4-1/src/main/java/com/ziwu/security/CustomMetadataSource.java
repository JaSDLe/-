package com.ziwu.security;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.ziwu.pojo.Menu;
import com.ziwu.pojo.Role;
import com.ziwu.service.MenuService;

@Component
public class CustomMetadataSource implements FilterInvocationSecurityMetadataSource{
	@Autowired
	MenuService menuService;
	AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		//获取请求地址
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		System.out.println("rq url:"+requestUrl);
		List<Menu> allMenu = menuService.getAllMenu();
		for (Menu menu : allMenu) {
			if (antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size()>0) {
				List<Role> roles = menu.getRoles();
				int size = roles.size();
				String[] values = new String[size];
				for (int i = 0; i < size; i++) {
					values[i] = roles.get(i).getName();
				}
				return SecurityConfig.createList(values);
			}
		}
		//没有匹配上的资源，都是登录访问
		return SecurityConfig.createList("ROLE_LOGIN");
	}
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}
