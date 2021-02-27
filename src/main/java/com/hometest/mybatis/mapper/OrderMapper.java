package com.hometest.mybatis.mapper;

import com.hometest.mybatis.domain.Cart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Bassam
 */
@Mapper
public interface OrderMapper {
    void cartCheckout(List<Cart> carts);
}
