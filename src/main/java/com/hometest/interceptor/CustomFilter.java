package com.hometest.interceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
