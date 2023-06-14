package com.don.productservice.service;

import com.don.productservice.dto.ProductCategoryResponse;
import com.don.productservice.dto.ProductResponse;
import com.don.productservice.dto.specification.RequestDto;
import com.don.productservice.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void saveProducts(Product product, String path, List<MultipartFile> images) throws IOException;
    List<ProductResponse> getAllProductByName(int offSet, int pageSize, String productAttribute);
    ProductResponse getProductById(Long id);
    void deleteProduct(Long id);
    ProductCategoryResponse getProductByCategory(String name);
    List<ProductResponse> getAll();
    ProductResponse getProductByName(String name);
    List<ProductResponse> getBySpecification(RequestDto requestDto);
    void updateStock(String productName, double quantity);
}
