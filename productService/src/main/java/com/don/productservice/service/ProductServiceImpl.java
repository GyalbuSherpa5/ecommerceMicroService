package com.don.productservice.service;

import com.don.productservice.dto.ProductCategoryResponse;
import com.don.productservice.mapper.ProductCategoryResponseMapper;
import com.don.productservice.dto.ProductResponse;
import com.don.productservice.mapper.ProductResponseMapper;
import com.don.productservice.eunm.ProductStatus;
import com.don.productservice.exception.ProductAlreadyExistException;
import com.don.productservice.exception.ProductCategoryDoNotExistException;
import com.don.productservice.exception.ProductDoNotExistException;
import com.don.productservice.model.Product;
import com.don.productservice.model.ProductCategory;
import com.don.productservice.repository.ProductCategoryRepository;
import com.don.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductResponseMapper productMapper;
    private final ProductCategoryResponseMapper categoryMapper;

    @Override
    public void saveProducts(Product product) {

        // Checking if product category already exist in database
        Optional<ProductCategory> productCategory =
                categoryRepository.findByCategoryName(product.getCategory().getCategoryName().toLowerCase());

        // If exist then will save into old category else makes new category
        ProductCategory saveProductCategoryToDatabase = productCategory
                .orElse(ProductCategory.builder()
                        .categoryName(product.getCategory().getCategoryName().toLowerCase())
                        .description(product.getCategory().getDescription())
                        .build());

        if (productRepository.findByProductName(
                product.getProductName()).isPresent()) {
            log.info("Unable to add duplicate product");
            throw new ProductAlreadyExistException("This product already exist");
        } else {
            Product saveProductInDatabase = Product.builder()
                    .productName(product.getProductName().toLowerCase())
                    .description(product.getDescription())
                    .availability(ProductStatus.AVAILABLE)
                    .price(product.getPrice())
                    .stockQuantity(product.getStockQuantity())
                    .category(saveProductCategoryToDatabase)
                    .build();

            productRepository.save(saveProductInDatabase);
            log.info("product successfully saved to database");
        }
    }

    @Override
    public List<ProductResponse> getAllProductByName(
            int offSet, int pageSize, String productAttribute) {

        Page<Product> allProductsByName = productRepository.findAll(
                PageRequest.of(offSet, pageSize)
                        .withSort(Sort.Direction.ASC, productAttribute));

        log.info("Fetching all products by name");
        return allProductsByName
                .stream()
                .map(productMapper)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper)
                .orElseThrow(() -> new ProductDoNotExistException(
                        "Product with id " + id + " doesn't exist"));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductDoNotExistException(
                        "Product with id " + id + " doesn't exist"));

        productRepository.delete(product);
    }

    @Override
    public ProductCategoryResponse getProductByCategory(String name) {
        return categoryRepository.findByCategoryName(name)
                .map(categoryMapper)
                .orElseThrow(() -> new ProductCategoryDoNotExistException(
                        "Product with name " + name + " do not exist"
                ));
    }
}
