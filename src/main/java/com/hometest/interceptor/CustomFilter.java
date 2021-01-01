package com.hometest.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.hometest.service.MessageService;

public class CustomFilter implements Filter {
//
//	@Autowired
//	private MessageService messageService;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		CustomHttpRequestBody wrappedRequest = new CustomHttpRequestBody((HttpServletRequest) request);

		String requestBody = new String(wrappedRequest.getBody());
		System.out.println("request body inside doFilter method:" + requestBody);
		chain.doFilter(wrappedRequest, response);

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
