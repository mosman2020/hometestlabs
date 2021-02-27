package com.hometest.mybatis.mapper;

import com.hometest.mybatis.domain.Cart;
import com.hometest.mybatis.domain.Product;
import com.hometest.mybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Bassam
 */
@Mapper
public interface CartMapper {
    List<Cart> getUserCart(Long userId);

    void addToCart(User user, Product product, Integer qty);

    void removeFromCart(Long userId, Long cartId);

    Cart findByUserAndProduct(User user, Product product);

    void updateCart(Cart cart);
}
