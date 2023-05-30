package com.don.productservice.dto;

import com.don.productservice.eunm.ProductStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String productName;
    private String description;
    private double price;
    private double stockQuantity;
    private ProductStatus availability;
}
