package com.don.orderservice.model.order;

import com.don.orderservice.dto.CartResponse;
import com.don.orderservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<Address> address;

    @CreationTimestamp
    private LocalDate orderedDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Long userId;

    @Transient
    private CartResponse cartResponse;

    private double totalPayment;

    private String paymentMethod;

}
