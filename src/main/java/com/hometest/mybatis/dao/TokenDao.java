package com.hometest.mybatis.dao;

import com.hometest.mybatis.domain.TokenBlackList;
import com.hometest.mybatis.mapper.TokenMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bassam
 */
@Component
public class TokenDao implements TokenMapper {
    @Autowired
    private SqlSession sqlSession;

    @Override
    public TokenBlackList findByToken(String token) {
        return this.sqlSession.selectOne("findToken", token);
    }

    @Override
    public void insertToken(TokenBlackList token) {
        this.sqlSession.insert("insertToken", token);
    }
}
