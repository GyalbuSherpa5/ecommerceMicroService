package com.don.orderservice.service;

import com.don.orderservice.dto.CartResponse;
import com.don.orderservice.dto.order.OrderRequestDto;
import com.don.orderservice.enums.OrderStatus;
import com.don.orderservice.feignClient.UserService;
import com.don.orderservice.model.order.Order;
import com.don.orderservice.repository.OrderRepository;
import com.don.orderservice.util.OrderTotalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final OrderTotalUtil totalUtil;

    @Override
    public void placeOrder(OrderRequestDto order, String userName) {
        Order saveOrderToDatabase = new Order();
        saveOrderToDatabase.setOrderStatus(OrderStatus.PROCESSING);

        saveOrderToDatabase.setAddress(order.getAddress());

        saveOrderToDatabase.setUserId(userService.getUserId(userName));

        CartResponse cartResponse = cartService.getMyCart(userName);

        saveOrderToDatabase.setTotalPayment(totalUtil.calculateTotal(cartResponse));

        orderRepository.save(saveOrderToDatabase);
    }
}
