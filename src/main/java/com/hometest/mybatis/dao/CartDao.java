package com.hometest.mybatis.dao;
import com.hometest.mybatis.domain.Cart;
import com.hometest.mybatis.domain.Product;
import com.hometest.mybatis.domain.User;
import com.hometest.mybatis.mapper.CartMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bassam
 */
@Component
public class CartDao implements CartMapper {

    private final SqlSession sqlSession;

    @Autowired
    public CartDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<Cart> getUserCart(Long userId) {
        Map<String, Long> params = new HashMap<>();
        params.put("userId", userId);
        return sqlSession.selectList("getUserCart", params);
    }

    @Override
    public void addToCart(User user, Product product, Integer qty) {
        Cart cart = Cart.builder()
                .productId(product.getId())
                .userId(user.getUserId())
                .qty(qty)
                .build();
        this.sqlSession.insert("addToCart", cart);
    }

    @Override
    public void removeFromCart(Long userId, Long cartId) {
        Map<String, Long> params = new HashMap<>();
        params.put("userId", userId);
        params.put("cartId", cartId);
        this.sqlSession.delete("removeFromCart", params);
    }

    @Override
    public Cart findByUserAndProduct(User user, Product product) {
        Map<String, Long> params = new HashMap<>();
        params.put("userId", user.getUserId());
        params.put("productId", product.getId());
        return this.sqlSession.selectOne("getUserCartProduct", params);
    }

    @Override
    public void updateCart(Cart cart) {
        cart.setQty(cart.getQty()+1);
        this.sqlSession.update("updateCartQty", cart);
    }
}
