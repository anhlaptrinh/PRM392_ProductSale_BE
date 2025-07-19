package com.prm.productsale.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity(name = "ReviewVotes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewVoteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int voteID;

  @ManyToOne
  @JoinColumn(name = "ReviewID")
  ReviewEntity review;

  @ManyToOne
  @JoinColumn(name = "UserID")
  UserEntity user;

  @Column(name = "VoteType")
  String voteType; // "up" | "down"

  @Column(name = "VotedAt")
  LocalDateTime votedAt = LocalDateTime.now();
}

