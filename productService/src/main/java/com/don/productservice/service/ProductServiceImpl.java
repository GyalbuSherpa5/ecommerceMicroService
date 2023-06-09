package com.don.productservice.service;

import com.don.productservice.dto.ProductCategoryResponse;
import com.don.productservice.dto.ProductResponse;
import com.don.productservice.dto.specification.RequestDto;
import com.don.productservice.eunm.ProductStatus;
import com.don.productservice.exception.ProductAlreadyExistException;
import com.don.productservice.exception.ProductCategoryDoNotExistException;
import com.don.productservice.exception.ProductDoNotExistException;
import com.don.productservice.mapper.ProductCategoryResponseMapper;
import com.don.productservice.mapper.ProductResponseMapper;
import com.don.productservice.model.Image;
import com.don.productservice.model.Product;
import com.don.productservice.model.ProductCategory;
import com.don.productservice.repository.ProductCategoryRepository;
import com.don.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    private final FilterSpecification<Product> productFilterSpecification;

    @Override
    public void saveProducts(Product product, String path, List<MultipartFile> images) throws IOException {

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

            String relativePath = path + "images/" + product.getProductName();
            String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;

            File filePath = new File(absolutePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            List<Image> imagesToSave = new ArrayList<>();

            for (MultipartFile image : images) {
                Files.copy(image.getInputStream(),
                        Paths.get(absolutePath, image.getOriginalFilename()));
                Image imageMultiPartToImage = new Image();
                imageMultiPartToImage.setUrl(relativePath + File.separator + image.getOriginalFilename());

                imagesToSave.add(imageMultiPartToImage);
            }

            Product saveProductInDatabase = Product.builder()
                    .productName(product.getProductName().toLowerCase())
                    .description(product.getDescription())
                    .availability(ProductStatus.AVAILABLE)
                    .images(imagesToSave)
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

        product.setAvailability(ProductStatus.DELETED);
        productRepository.save(product);
    }

    @Override
    public ProductCategoryResponse getProductByCategory(String name) {
        return categoryRepository.findByCategoryName(name)
                .map(categoryMapper)
                .orElseThrow(() -> new ProductCategoryDoNotExistException(
                        "Product with name " + name + " do not exist"
                ));
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductByName(String name) {
        return productRepository.findByProductName(name)
                .map(productMapper)
                .orElseThrow(() -> {
                    log.error("Error retrieving product by name: {}", name);
                    return new ProductDoNotExistException("This product does not exist");
                });
    }

    @Override
    public List<ProductResponse> getBySpecification(RequestDto requestDto) {
        Specification<Product> searchSpecification = productFilterSpecification.getSearchSpecification(
                requestDto.getSearchRequestDto(), requestDto.getGlobalOperator());
        return productRepository.findAll(searchSpecification)
                .stream()
                .map(productMapper)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStock(String productName, double quantity) {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() -> {
                    log.error("product not found");
                    return new ProductDoNotExistException("this product does not exist");
                });

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }
}
