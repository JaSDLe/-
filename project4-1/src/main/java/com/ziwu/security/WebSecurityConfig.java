package com.ziwu.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziwu.common.UserUtils;
import com.ziwu.pojo.RespBean;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	CustomMetadataSource metadataSource;
	@Autowired
	UrlAccessDecisionManager urlAccessDecisionManager;
	@Autowired
	AuthenticationAccessDeniedHandler deniedHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/index.html", "/static/**", "/login_p");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//				.antMatchers("/","/index").permitAll()
//				.anyRequest().authenticated()
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O object) {
						object.setSecurityMetadataSource(metadataSource);
						object.setAccessDecisionManager(urlAccessDecisionManager);
						return object;
					}
				})
			.and()
				.formLogin()
				.loginPage("/login_p") //指定登录页
				.loginProcessingUrl("/login") //登录url
//				.defaultSuccessUrl("/ok")
				.usernameParameter("username")
				.passwordParameter("password")
				.failureHandler(new AuthenticationFailureHandler() {
					@Override
					public void onAuthenticationFailure(HttpServletRequest request, 
															HttpServletResponse response,
															AuthenticationException e) throws IOException, ServletException {
						response.setContentType("application/json;charset=utf-8");
						RespBean respBean = null;
						if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
							respBean = RespBean.error("用户名或者密码输入错误!");
						} else if (e instanceof LockedException) {
							respBean = RespBean.error("账户被锁定，请联系管理员!");
						} else if (e instanceof CredentialsExpiredException) {
							respBean = RespBean.error("密码过期，请联系管理员!");
						} else if (e instanceof AccountExpiredException) {
							respBean = RespBean.error("账户过期，请联系管理员!");
						} else if (e instanceof DisabledException) {
							respBean = RespBean.error("账户被禁用，请联系管理员!");
						} else {
							respBean = RespBean.error("登录失败!");
						}
						response.setStatus(401);
						ObjectMapper om = new ObjectMapper();
						PrintWriter out = response.getWriter();
						out.write(om.writeValueAsString(respBean));
						out.flush();
						out.close();
					}
				})
				.successHandler(new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, 
															HttpServletResponse response,
															Authentication authentication) throws IOException, ServletException {
						response.setContentType("application/json;charset=utf-8");
						RespBean respBean = RespBean.ok("登录成功!", UserUtils.getCurrentUser());
						ObjectMapper om = new ObjectMapper();
						PrintWriter out = response.getWriter();
						out.write(om.writeValueAsString(respBean));
						out.flush();
						out.close();
					}
				})
				.permitAll()
			.and()
				.logout()
				.logoutUrl("/logout")
//				.logoutSuccessUrl("/index")
				.logoutSuccessHandler(new LogoutSuccessHandler() {
					@Override
					public void onLogoutSuccess(HttpServletRequest request, 
													HttpServletResponse response, 
													Authentication authentication) throws IOException, ServletException {
						response.setContentType("application/json;charset=utf-8");
						RespBean respBean = RespBean.ok("注销成功!");
						ObjectMapper om = new ObjectMapper();
						PrintWriter out = response.getWriter();
						out.write(om.writeValueAsString(respBean));
						out.flush();
						out.close();
					}
				})
				.permitAll()
			.and()
				.csrf().disable()
				.exceptionHandling().accessDeniedHandler(deniedHandler);
	}
}
