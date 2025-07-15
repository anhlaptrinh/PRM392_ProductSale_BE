package com.prm.productsale.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
  int reviewID;
  int productID;
  int userID;
  String username;
  int rating;
  String comment;
  int helpfulCount;
  LocalDateTime createdAt;
}
