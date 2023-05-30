package com.don.productservice.mapper;

import com.don.productservice.dto.ProductCategoryResponse;
import com.don.productservice.model.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryResponseMapper implements Function<ProductCategory, ProductCategoryResponse> {

    private final ProductResponseMapper mapper;

    @Override
    public ProductCategoryResponse apply(ProductCategory productCategory) {
        return new ProductCategoryResponse(
                productCategory.getCategoryName(),
                productCategory.getDescription(),
                productCategory.getProducts()
                        .stream()
                        .map(mapper)
                        .collect(Collectors.toList())
        );
    }
}
