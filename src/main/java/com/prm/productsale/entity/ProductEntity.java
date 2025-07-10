package com.prm.productsale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "Products")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    int id;
    @Column(name = "ProductName")
    String productName;
    @Column(name = "BriefDescription")
    String briDesc;
    @Column(name = "FullDescription")
    String fullDesc;
    @Column(name = "TechnicalSpecifications")
    String technic;
    @Column(name = "Price")
    BigDecimal price;
    @Column(name = "ImageURL")
    String imageURL;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    CategoryEntity category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<CartItemEntity> cartItems;


    @OneToMany(mappedBy = "product")
    List<WishlistEntity> wishlist;



}
