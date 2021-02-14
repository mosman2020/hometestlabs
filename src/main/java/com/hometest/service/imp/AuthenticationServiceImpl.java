package com.hometest.service.imp;

import com.hometest.mybatis.domain.User;
import com.hometest.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getUser() {

        Object principal = getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            return  ((UserDetailsImpl)principal).getUser();
        }
        return null;
    }

    @Override
    public UserDetailsImpl getPrinciples() {
        Object principal = getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            return  (UserDetailsImpl)principal;
        }
        return null;
    }
}
