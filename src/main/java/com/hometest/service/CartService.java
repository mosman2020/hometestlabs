package com.hometest.service;

import com.hometest.model.req.AddToCartRequest;
import com.hometest.mybatis.domain.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getUserCart(Long userId);

    void addToCart(Long userId, AddToCartRequest request);

    void removeFromCart(Long userId, Long cartId);

    void checkout(Long userId);
}
