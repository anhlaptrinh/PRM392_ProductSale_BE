package com.prm.productsale.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity(name = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    int id;
    @ManyToOne
    @JoinColumn(name = "CartID")
    CartEntity cart;
    @ManyToOne
    @JoinColumn(name = "UserID")
    UserEntity user;
    @Column(name = "PaymentMethod")
    String pmMethod;
    @Column(name = "BillingAddress")
    String bill;
    @Column(name = "OrderStatus")
    String orderStatus;
    @Column(name = "OrderDate")
    LocalDateTime orderDate;

}
