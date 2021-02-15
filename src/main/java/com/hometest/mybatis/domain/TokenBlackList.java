package com.hometest.mybatis.domain;

import lombok.Data;

@Data
public class TokenBlackList {
    private Long id;
    private String token;
    private User user;
}
