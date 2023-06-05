package com.don.orderservice.util;

import com.don.orderservice.dto.CartItemResponse;
import com.don.orderservice.dto.CartResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderTotalUtil {
    public double calculateTotal(CartResponse cartResponse) {
        List<CartItemResponse> cartItemResponses = cartResponse.getCartItemResponses();

        double total = 0;

        for (CartItemResponse cartItems : cartItemResponses) {
            double orderedQuantity = cartItems.getOrderedQuantity();
            double price = cartItems.getPrice();
            total += orderedQuantity * price;
        }
        return total;
    }
}
