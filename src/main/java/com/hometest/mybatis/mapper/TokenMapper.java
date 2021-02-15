package com.hometest.mybatis.mapper;

import com.hometest.mybatis.domain.TokenBlackList;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Bassam
 */
@Mapper
public interface TokenMapper {
    TokenBlackList findByToken(String token);
    void insertToken(TokenBlackList token);
}
