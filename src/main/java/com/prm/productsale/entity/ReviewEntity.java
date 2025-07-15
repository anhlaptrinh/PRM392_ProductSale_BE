package com.prm.productsale.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity(name = "Reviews")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ReviewID")
  int reviewID;

  @ManyToOne
  @JoinColumn(name = "ProductID", nullable = false)
  ProductEntity product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "UserID", nullable = false)
  UserEntity user;

  @Column(name = "Rating", nullable = false)
  int rating;

  @Column(name = "Comment")
  String comment;

  @Column(name = "HelpfulCount")
  int helpfulCount = 0;

  @Column(name = "CreatedAt")
  LocalDateTime createdAt = LocalDateTime.now();
}
