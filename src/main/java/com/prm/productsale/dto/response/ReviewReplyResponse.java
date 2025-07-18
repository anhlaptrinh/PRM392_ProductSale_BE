package com.prm.productsale.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewReplyResponse {
  int replyID;
  int reviewID;
  int repliedBy;
  String username;
  String replyText;
  String repliedAt;
}
