package com.prm.productsale.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import jakarta.persistence.Column;


import java.time.LocalDateTime;
@Data
@Entity(name = "Notifications")

public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private UserEntity user;

    @Column(name = "Message")
    private String content;

    @Column(name = "IsRead")
    private boolean isRead = false;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();
}
