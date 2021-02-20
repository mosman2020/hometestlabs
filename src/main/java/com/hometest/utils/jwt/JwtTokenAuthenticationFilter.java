package com.hometest.utils.jwt;

import com.hometest.model.res.TokenData;
import com.hometest.service.imp.UserDetailsImpl;
import com.hometest.service.imp.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			logger.info("from AuthTokenFilter request : "+request);
			String jwt = jwtUtils.parseJwt(request);
//			logger.info("jwt:"+jwt);
			logger.info("jwt : "+jwt);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)  && !jwtUtils.isTokenInBlackList(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				TokenData token = TokenData.builder().token(jwt).userid(userDetails.getUser().getUserId()).build();
				userDetails.setToken(token);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}
}
