package com.prm.productsale.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "Wishlist")
public class WishlistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WishlistID")
    int id;


    @ManyToOne
    @JoinColumn(name = "UserID")
    UserEntity user;


    @ManyToOne
    @JoinColumn(name = "ProductID")
    ProductEntity product;

}
