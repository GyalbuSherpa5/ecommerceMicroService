package com.don.orderservice.service;

import com.don.orderservice.dto.CartResponse;
import com.don.orderservice.model.CartItem;

public interface CartService {
    void saveToCart(CartItem cartItem, String username);
    CartResponse getMyCart(String username);
}
