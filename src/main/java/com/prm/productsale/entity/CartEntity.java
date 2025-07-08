package com.prm.productsale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "Carts")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    int id;
    @ManyToOne
    @JoinColumn(name = "UserID")
    UserEntity user;
    @Column(name = "TotalPrice")
    BigDecimal total;
    @Column(name = "Status")
    String status;
    @JsonIgnore
    @OneToMany(mappedBy = "cart")
    List<OrderEntity> orders;
    @JsonIgnore
    @OneToMany(mappedBy = "cart")
    List<CartItemEntity> cartItems;
}
