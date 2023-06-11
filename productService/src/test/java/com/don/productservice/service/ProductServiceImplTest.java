package com.don.productservice.service;

import com.don.productservice.dto.ProductResponse;
import com.don.productservice.eunm.ProductStatus;
import com.don.productservice.exception.ProductAlreadyExistException;
import com.don.productservice.exception.ProductDoNotExistException;
import com.don.productservice.mapper.ProductResponseMapper;
import com.don.productservice.model.Image;
import com.don.productservice.model.Product;
import com.don.productservice.model.ProductCategory;
import com.don.productservice.repository.ProductCategoryRepository;
import com.don.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductCategoryRepository categoryRepository;
    @Mock
    private ProductResponseMapper productResponseMapper;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void saveProducts_WhenProductDoesNotExist_ShouldSaveProductToDatabase() throws IOException {
        // Arrange
        String productName = "Test Product";
        String categoryName = "Test Category";
        String description = "Test Description";
        double price = 10.0;
        int stockQuantity = 100;

        ProductCategory productCategory = ProductCategory.builder()
                .categoryName(categoryName)
                .description(description)
                .build();

        Product product = new Product();
        product.setProductName(productName);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setCategory(productCategory); // Assign the product category to the product

        List<MultipartFile> images = new ArrayList<>();
        MockMultipartFile image1 = new MockMultipartFile("image", "image1.jpg", "image/jpeg", "data".getBytes());
        images.add(image1);

        // Configure the mock categoryRepository
        when(categoryRepository.findByCategoryName(categoryName.toLowerCase())).thenReturn(Optional.ofNullable(productCategory));
        when(categoryRepository.save(any(ProductCategory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Configure the mock productRepository
        when(productRepository.findByProductName(productName)).thenReturn(Optional.empty());

        // Act
        productService.saveProducts(product, "path/to/images/", images);

        // Assert
        verify(categoryRepository, times(1)).findByCategoryName(categoryName.toLowerCase());
        verify(categoryRepository, times(1)).save(any(ProductCategory.class));
        verify(productRepository, times(1)).findByProductName(productName);
        verify(productRepository, times(1)).save(any(Product.class));
    }



    @Test
    void saveProducts_WhenProductAlreadyExists_ShouldThrowProductAlreadyExistException(){
        // Arrange
        String productName = "Existing Product";
        String categoryName = "Test Category";
        String description = "Test Description";
        double price = 10.0;
        int stockQuantity = 100;

        Product product = new Product();
        product.setProductName(productName);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);

        ProductCategory productCategory = ProductCategory.builder()
                .categoryName(categoryName)
                .description(description)
                .build();

        List<MultipartFile> images = new ArrayList<>();
        MockMultipartFile image1 = new MockMultipartFile("image", "image1.jpg", "image/jpeg", "data".getBytes());
        images.add(image1);

        // Configure the mock categoryRepository
        when(categoryRepository.findByCategoryName(categoryName.toLowerCase())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(ProductCategory.class))).thenReturn(productCategory);

        // Configure the mock productRepository
        when(productRepository.findByProductName(productName.toLowerCase())).thenReturn(Optional.of(product));

        // Act & Assert
        assertThrows(ProductAlreadyExistException.class,
                () -> productService.saveProducts(product, "path/to/images/", images));

        // Assert
        verify(categoryRepository, times(1)).findByCategoryName(categoryName.toLowerCase());
        verify(categoryRepository, times(1)).save(any(ProductCategory.class));
        verify(productRepository, times(1)).findByProductName(productName.toLowerCase());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProductResponse() {
        // Arrange
        Long productId = 1L;

        // Create a mock Product object
        Product product = new Product();
        product.setDescription("Test Description");
        product.setProductName("Test Product");
        product.setPrice(10.0);
        product.setStockQuantity(100);
        product.setAvailability(ProductStatus.AVAILABLE);

        List<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setImageId(1L);
        image.setUrl("productService/src/main/resources/static/test.jpg");
        images.add(image);
        product.setImages(images);

        // Configure the mock productRepository
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Create the expected ProductResponse
        List<Image> expectedImages = new ArrayList<>();
        Image expectedImage = new Image();
        expectedImage.setImageId(1L);
        expectedImage.setUrl("http://localhost:8080/test.jpg");
        expectedImages.add(expectedImage);

        ProductResponse expectedProductResponse = new ProductResponse(
                "Test Description",
                "Test Product",
                10.0,
                100,
                ProductStatus.AVAILABLE,
                expectedImages
        );

        // Configure the mock productResponseMapper
        when(productResponseMapper.apply(product)).thenReturn(expectedProductResponse);

        // Act
        ProductResponse actualProductResponse = productService.getProductById(productId);

        // Assert
        assertEquals(expectedProductResponse, actualProductResponse);
    }

    @Test
    void getProductById_WhenProductDoesNotExist_ShouldThrowProductDoNotExistException() {
        // Arrange
        Long productId = 1L;

        // Configure the mock productRepository
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductDoNotExistException.class, () -> productService.getProductById(productId));
    }
}
