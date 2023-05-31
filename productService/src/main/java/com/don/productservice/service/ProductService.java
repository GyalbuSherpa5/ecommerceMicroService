package com.don.productservice.service;

import com.don.productservice.dto.ProductCategoryResponse;
import com.don.productservice.dto.ProductResponse;
import com.don.productservice.model.Product;

import java.util.List;

public interface ProductService {
    void saveProducts(Product product);
    List<ProductResponse> getAllProductByName(int offSet, int pageSize, String productAttribute);
    ProductResponse getProductById(Long id);
    void deleteProduct(Long id);
    ProductCategoryResponse getProductByCategory(String name);
    List<ProductResponse> getAll();
}
