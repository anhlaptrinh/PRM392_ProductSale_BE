package com.prm.productsale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "ChatMessages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChatMessageID")
    private int id;

    @Column(name = "Message")
    private String mess;

    @Column(name = "SentAt")
    private LocalDateTime sentDate;

    @ManyToOne
    @JoinColumn(name = "UserID")
    UserEntity user;


}
