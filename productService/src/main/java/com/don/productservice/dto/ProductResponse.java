package com.don.productservice.dto;

import com.don.productservice.eunm.ProductStatus;
import com.don.productservice.model.Image;
import lombok.*;

import java.util.List;

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
    private List<Image> images;
}
