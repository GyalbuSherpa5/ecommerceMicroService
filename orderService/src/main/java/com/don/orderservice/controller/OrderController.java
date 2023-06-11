package com.don.orderservice.controller;

import com.don.orderservice.dto.order.OrderRequestDto;
import com.don.orderservice.dto.order.OrderResponseDto;
import com.don.orderservice.service.EmailService;
import com.don.orderservice.service.OrderService;
import com.don.orderservice.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final AuthUtil authUtil;
    private final EmailService emailService;

    @PostMapping("/sendMail")
    public String sendEmail(
            String to,
            String[] cc,
            String subject, String body){
        return emailService.sendMail(to,cc,subject,body);
    }

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
    @GetMapping("/getOrder")
    public List<OrderResponseDto> getOrder(
            @RequestHeader("Authorization") String authorizationHeader
           ) {

        String userName = authUtil.getUserName(authorizationHeader);
        if (userName == null) {
            return null;
        }
        return orderService.getMyOrder(userName);
    }

}
