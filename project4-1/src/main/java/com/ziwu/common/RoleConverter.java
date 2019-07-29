package com.ziwu.common;

import org.springframework.core.convert.converter.Converter;

import com.ziwu.pojo.Role;

public class RoleConverter implements Converter<String, Role>{

	@Override
	public Role convert(String s) {
		if ("".equals(s) || s == null) {
            return null;
        }
		System.out.println("RoleConverter:"+s);
		return null;
	}

}
