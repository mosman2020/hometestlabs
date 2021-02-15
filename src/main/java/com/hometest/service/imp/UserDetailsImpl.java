package com.hometest.service.imp;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hometest.enums.UserStatus;
import com.hometest.model.res.TokenData;
import com.hometest.mybatis.domain.User;


public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private User user;
	private TokenData token;
	
	UserDetailsImpl(User user){
		this.user= user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return user.getPassword().getPasswordValue();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;//!UserStatus.CREATED.getValue().equals(user.getUserStatus());
	}

	@Override
	public boolean isAccountNonLocked() {
		return  !UserStatus.LOCKED.getValue().equals(user.getUserStatus());
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.getPassword().getExpiryDate()!=null?user.getPassword().getExpiryDate().after(new Date()):true;
	}

	@Override
	public boolean isEnabled() {
		return !UserStatus.DISABLED.getValue().equals(user.getUserStatus());
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public TokenData getTokenData() {
		return TokenData.builder().userid(user.getUserId()).build();
	}

	public void setToken(TokenData token){
		this.token = token;
	}
}
