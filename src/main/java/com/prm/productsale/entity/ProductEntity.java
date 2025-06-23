package com.prm.productsale.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity(name = "Products")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int ProductID;
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
  String image;
  @ManyToOne
  @JoinColumn(name = "CategoryID")
  CategoryEntity category;
}
