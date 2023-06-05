package com.don.orderservice.dto.order;

import com.don.orderservice.dto.CartResponse;
import com.don.orderservice.enums.OrderStatus;
import com.don.orderservice.model.order.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private List<Address> address;

    private LocalDate orderedDate;

    private OrderStatus orderStatus;

    private Long userId;

    private CartResponse cartResponse;

    private double totalPayment;
}
