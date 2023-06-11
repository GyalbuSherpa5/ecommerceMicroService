package com.don.productservice.mapper;

import com.don.productservice.dto.ProductResponse;
import com.don.productservice.model.Image;
import com.don.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class ProductResponseMapper implements Function<Product, ProductResponse> {
    @Override
    public ProductResponse apply(Product product) {

        List<Image> images = product.getImages();
        List<Image> newImages = new ArrayList<>();

        for (Image image : images) {

            String saveImagePath = image.getUrl().replace("productService/src/main/resources/static/","");

            Image image1 = new Image();
            image1.setUrl("http://localhost:8080/" + saveImagePath);
            image1.setImageId(image.getImageId());

            newImages.add(image1);
        }

        return new ProductResponse(
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getAvailability(),
                newImages
        );
    }
}
