package com.don.productservice.model;

import com.don.productservice.eunm.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private double price;
    private double stockQuantity;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus availability;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private ProductCategory category;
}
