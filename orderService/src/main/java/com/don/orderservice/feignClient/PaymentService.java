package com.don.orderservice.feignClient;

import com.don.orderservice.dto.order.PaymentRequestDto;
import com.don.orderservice.dto.order.PaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "external", url = "https://acca33c3-19e4-40b1-8e64-a2b3050d451e.mock.pstmn.io")
public interface PaymentService {

    @PostMapping("/makePayment")
    PaymentResponseDto makePayment(@RequestBody PaymentRequestDto paymentRequestDto);

}
