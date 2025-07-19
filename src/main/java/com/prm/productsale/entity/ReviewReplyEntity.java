package com.prm.productsale.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity(name = "ReviewReplies")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReplyEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int replyID;

  int reviewID;

  int repliedBy;

  @Column(columnDefinition = "text")
  String replyText;

  LocalDateTime repliedAt;
}
