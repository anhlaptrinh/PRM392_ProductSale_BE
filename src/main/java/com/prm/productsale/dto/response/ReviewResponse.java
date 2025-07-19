package com.prm.productsale.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
  int reviewID;
  int productID;
  int userID;
  String username;
  String email;
  int rating;
  String comment;
  int helpfulCount;
  String createdAt;
  List<ReviewVoteResponse> voteList;
}
