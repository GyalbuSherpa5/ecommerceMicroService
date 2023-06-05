package com.don.orderservice.service;

import com.don.orderservice.dto.order.OrderRequestDto;

public interface OrderService {
    void placeOrder(OrderRequestDto order, String userName);
}
