package com.ziwu.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziwu.pojo.RespBean;

@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, 
						HttpServletResponse response,
						AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json;charset=utf-8");
		RespBean respBean = RespBean.error("权限不足，请联系管理员!");
		ObjectMapper om = new ObjectMapper();
		PrintWriter out = response.getWriter();
		out.write(om.writeValueAsString(respBean));
		out.flush();
		out.close();
	}
}
