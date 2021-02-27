package com.hometest.service.imp;

import com.hometest.exceptionhandling.exception.BusinessException;
import com.hometest.model.req.AddToCartRequest;
import com.hometest.mybatis.dao.CartDao;
import com.hometest.mybatis.domain.Cart;
import com.hometest.mybatis.domain.Product;
import com.hometest.mybatis.domain.User;
import com.hometest.service.CartService;
import com.hometest.service.ProductService;
import com.hometest.service.UserService;
import com.hometest.utils.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartServiceImpl(CartDao cartDao, UserService userService, ProductService productService) {
        this.cartDao = cartDao;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public List<Cart> getUserCart(Long userId) {
        userService.assertLoggedUserIsTheSame(userId);
        return cartDao.getUserCart(userId);
    }

    @Override
    public void addToCart(Long userId, AddToCartRequest request) {
        userService.assertLoggedUserIsTheSame(userId);
        if(request.getProductId() <= 0 )
            throw new BusinessException(ErrorCodes.INVALID_PRODUCT);
        if(request.getQty() == null || request.getQty() <= 0 )
            throw new BusinessException(ErrorCodes.INVALID_QTY);
        final Product product = productService.findById(request.getProductId());
        final User user = userService.getByUserId(userId);
        Cart cart = cartDao.findByUserAndProduct(user, product);
        if(cart != null){
            cartDao.updateCart(cart);
        } else {
            cartDao.addToCart(user, product, request.getQty());
        }

    }

    @Override
    public void removeFromCart(Long userId, Long cartId) {
        userService.assertLoggedUserIsTheSame(userId);
        cartDao.removeFromCart(userId, cartId);
    }

    @Override
    public void checkout(Long userId) {
        userService.assertLoggedUserIsTheSame(userId);
        List<Cart> carts = getUserCart(userId);
    }

}
