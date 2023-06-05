package com.don.orderservice.controller;

import com.don.orderservice.dto.order.OrderRequestDto;
import com.don.orderservice.service.OrderService;
import com.don.orderservice.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final AuthUtil authUtil;

    @PostMapping("/placeOrder")
    public String placeOrder(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody OrderRequestDto order) {

        String userName = authUtil.getUserName(authorizationHeader);
        if (userName == null) {
            return "error";
        }
        orderService.placeOrder(order, userName);
        return "Order placed successfully";
    }

}
