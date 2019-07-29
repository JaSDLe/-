package com.ziwu.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, 
											HttpServletResponse response, 
											Object handler,
											Exception ex) {
		ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
		Map<String, Object> map = new HashMap<>();
		map.put("status", 500);
		map.put("msg", "操作失败！");
		mv.addAllObjects(map);
		return mv;
	}

}
