package com.hometest.utils;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.hometest.interceptor.CustomFilter;
import com.hometest.jobs.UnlockUsersJob;
import com.hometest.service.imp.UserDetailsServiceImpl;
import com.hometest.utils.jwt.JwtAuthenticationEntryPoint;
import com.hometest.utils.jwt.JwtTokenAuthenticationFilter;
import com.hometest.utils.jwt.CustomAuthenticationFailureHandler;
import com.hometest.utils.jwt.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint authEntryPointJwt;
	
	@Bean
	public JwtTokenAuthenticationFilter authenticationJwtTokenFilter() {
		return new JwtTokenAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	   public FilterRegistrationBean<CustomFilter> customFilter(){
	       FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean();
	           
	       registrationBean.setFilter(new CustomFilter());
	       registrationBean.addUrlPatterns("/*");
	           
	       return registrationBean;    
	   }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/signin","/api/userManagement/signup").permitAll()
			.antMatchers("/api/test/**").permitAll()
			.anyRequest().authenticated();
	
//		http.formLogin().successHandler(authenticationSuccessHandler());
//		http.formLogin().failureHandler(authenticationFailureHandler());
//		http.addFilterBefore(authenticationJwtTokenFilter(), BasicAuthenticationFilter.class)
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
