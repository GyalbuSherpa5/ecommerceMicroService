package com.don.orderservice.feignClient;

import com.don.orderservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "product-service")
public interface ProductService {
    @GetMapping("/products/getProductByName/{name}")
    ProductResponse getProductByName(@PathVariable String name);

    @PutMapping("/updateProductStock/{productName}/{quantity}")
    public String updateProductStock(@PathVariable String productName, @PathVariable double quantity);


}
