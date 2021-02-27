package com.hometest.controllers;


import com.hometest.model.req.AddToCartRequest;
import com.hometest.mybatis.domain.Cart;
import com.hometest.service.CartService;
import com.hometest.utils.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Bassam
 */
@RestController
@RequestMapping(CartController.API_END_POINT)
public class CartController {
    final static protected String API_END_POINT = WebConfig.API_END_POINT + "/cart/{userId}";

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getUserCart(@PathVariable Long userId){
        return cartService.getUserCart(userId);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void addToCart(@PathVariable Long userId, @RequestBody AddToCartRequest request){
        cartService.addToCart(userId, request);
    }

    @DeleteMapping("/remove/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFromCart(@PathVariable Long userId, @PathVariable Long cartId ){
        cartService.removeFromCart(userId, cartId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void checkout(@PathVariable Long userId){
        cartService.checkout(userId);
    }

}
