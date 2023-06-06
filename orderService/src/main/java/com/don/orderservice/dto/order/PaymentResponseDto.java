package com.don.orderservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private double amount;
    private String transaction_code;
    private String unique_id;
    private String status;

}
