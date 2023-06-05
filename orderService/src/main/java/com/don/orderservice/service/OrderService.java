package com.don.orderservice.service;

import com.don.orderservice.dto.order.OrderRequestDto;
import com.don.orderservice.dto.order.OrderResponseDto;

import java.util.List;

public interface OrderService {
    void placeOrder(OrderRequestDto order, String userName);
    List<OrderResponseDto> getMyOrder(String userName);
}
