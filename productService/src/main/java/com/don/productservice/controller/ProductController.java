package com.don.productservice.controller;

import com.don.productservice.dto.ProductCategoryResponse;
import com.don.productservice.dto.ProductResponse;
import com.don.productservice.model.Product;
import com.don.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAllProducts")
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }


    @PostMapping("/addProduct")
    public String addProduct(@RequestBody Product product) {
        productService.saveProducts(product);
        return "product added successfully";
    }

    @GetMapping("/getByAttribute/{offSet}/{pageSize}/{productAttribute}")
    public List<ProductResponse> getAllProductsByName(
            @PathVariable int offSet, // page number [starts from 0]
            @PathVariable int pageSize, // total element per page [starts from 1]
            @PathVariable String productAttribute) {

        return productService.getAllProductByName(offSet, pageSize, productAttribute);
    }

    @GetMapping("/getByCategoryName/{name}")
    public ProductCategoryResponse getAllProductByCategory(@PathVariable String name) {
        return productService.getProductByCategory(name);
    }

    @GetMapping("/getById/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/getProductByName/{name}")
    public ProductResponse getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "product with id " + id + " deleted successfully";
    }
}
