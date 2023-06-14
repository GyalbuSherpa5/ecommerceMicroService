package com.don.orderservice.service;

import com.don.orderservice.dto.CartItemResponse;
import com.don.orderservice.dto.CartResponse;
import com.don.orderservice.dto.order.OrderRequestDto;
import com.don.orderservice.dto.order.OrderResponseDto;
import com.don.orderservice.dto.order.PaymentRequestDto;
import com.don.orderservice.dto.order.PaymentResponseDto;
import com.don.orderservice.dto.user.UserResponse;
import com.don.orderservice.enums.OrderStatus;
import com.don.orderservice.feignClient.MailService;
import com.don.orderservice.feignClient.PaymentService;
import com.don.orderservice.feignClient.ProductService;
import com.don.orderservice.feignClient.UserService;
import com.don.orderservice.model.mail.Mail;
import com.don.orderservice.model.order.Order;
import com.don.orderservice.repository.OrderRepository;
import com.don.orderservice.util.OrderTotalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final OrderTotalUtil totalUtil;
    private final PaymentService paymentService;
    private final MailService mailService;
    private final ProductService productService;

    @Override
    public void placeOrder(OrderRequestDto order, String userName) {

        Order saveOrderToDatabase = new Order();
        saveOrderToDatabase.setOrderStatus(OrderStatus.PROCESSING);
        saveOrderToDatabase.setAddress(order.getAddress());
        saveOrderToDatabase.setUserId(userService.getUserId(userName));

        CartResponse cartResponse = cartService.getMyCart(userName);
        saveOrderToDatabase.setTotalPayment(totalUtil.calculateTotal(cartResponse));
        saveOrderToDatabase.setPaymentMethod(order.getPaymentMethod());

        orderRepository.save(saveOrderToDatabase);
        log.info("order saved successfully");

        PaymentRequestDto requestDto = new PaymentRequestDto();
        requestDto.setAmount(saveOrderToDatabase.getTotalPayment());
        PaymentResponseDto paymentResponseDto = paymentService.makePayment(requestDto);
        log.info(paymentResponseDto.getTransaction_code());

        Mail mail = new Mail();
        UserResponse user = userService.getUserByName(userName);
        mail.setTo(user.getEmail());
        mail.setSubject("Order placed");
        mail.setBody("Thank you for your order");
        mailService.sendEmail(mail);
        log.info("mail send successfully");

        List<CartItemResponse> cartItemResponses = cartResponse.getCartItemResponses();
        for (CartItemResponse cartItemResponse : cartItemResponses) {
            double orderedQuantity = cartItemResponse.getOrderedQuantity();
            String productName = cartItemResponse.getProductName();

            productService.updateProductStock(productName,orderedQuantity);

        }


        //TODO: create new service PaymentServiceCaller
        //TODO: call the server using restTemplate
        //TODO: get payment url,username,password from prop file
        //TODO; url baseUrl -> from property, endpoint code
    }

    @Override
    public List<OrderResponseDto> getMyOrder(String userName) {

        log.info("fetching orders");
        List<Order> orders = orderRepository.findByUserId(
                userService.getUserId(userName));

        List<OrderResponseDto> orderResponse = new ArrayList<>();
        for (Order order : orders) {
            OrderResponseDto responseDto = new OrderResponseDto();
            responseDto.setOrderStatus(order.getOrderStatus());
            responseDto.setAddress(order.getAddress());
            responseDto.setOrderedDate(order.getOrderedDate());
            responseDto.setUserId(order.getUserId());
            responseDto.setTotalPayment(order.getTotalPayment());
            responseDto.setCartResponse(cartService.getMyCart(userName));
            responseDto.setPaymentMethod(order.getPaymentMethod());

            orderResponse.add(responseDto);
        }
        return orderResponse;
    }

}
