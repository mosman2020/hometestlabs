
package com.hometest.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomHttpRequestBody extends HttpServletRequestWrapper {
	private final String body;

	public CustomHttpRequestBody(HttpServletRequest request) throws IOException {

		super(request);
		body = request.getReader().lines().collect(Collectors.joining());
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
		ServletInputStream servletInputStream = new ServletInputStream() {
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// TODO Auto-generated method stub

			}
		};
		return servletInputStream;
	}

	public String getBody() {
		return body;
	}
	
}
