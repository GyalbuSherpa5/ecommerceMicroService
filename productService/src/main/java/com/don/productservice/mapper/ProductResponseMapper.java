package com.don.productservice.mapper;

import com.don.productservice.dto.ProductResponse;
import com.don.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductResponseMapper implements Function<Product, ProductResponse> {
    @Override
    public ProductResponse apply(Product product) {
        return new ProductResponse(
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getAvailability()
        );
    }
}
