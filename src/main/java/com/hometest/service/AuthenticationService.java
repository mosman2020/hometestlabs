package com.hometest.service;

import com.hometest.mybatis.domain.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication getAuthentication();
    User getUser();
}
