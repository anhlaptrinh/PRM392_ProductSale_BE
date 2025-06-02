package com.prm.productsale.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "CartItems")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemID")
    int id;
    @ManyToOne
    @JoinColumn(name = "CartID")
    CartEntity cart;
    @ManyToOne
    @JoinColumn(name = "ProductID")
    ProductEntity product;
    @Column(name = "Quantity")
    int quantity;
    @Column(name = "Price")
    BigDecimal price;
}
