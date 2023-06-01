package com.don.orderservice.feignClient;

import com.don.orderservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "product-service", path = "/products")
public interface ProductService {
    @GetMapping("/getProductByName/{name}")
    ProductResponse getProductByName(@PathVariable String name);
}
