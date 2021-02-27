package com.hometest.mybatis.dao;
import com.hometest.mybatis.domain.Cart;
import com.hometest.mybatis.mapper.OrderMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author Bassam
 */
@Component
public class OrderDao implements OrderMapper {

    private final SqlSession sqlSession;

    @Autowired
    public OrderDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void cartCheckout(List<Cart> carts) {

    }
}
