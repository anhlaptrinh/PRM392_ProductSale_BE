package com.prm.productsale.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Payments")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    int id;

    @Column(name = "OrderID")
    int orderID;

    @Column(name = "Amount")
    BigDecimal totalAmount;

    @Column(name = "PaymentDate")
    LocalDateTime paymentDate;

    @Column(name = "PaymentStatus")
    String paymentStatus;

    // Nếu cần quan hệ với OrderEntity thì có thể thêm:
    @ManyToOne
    @JoinColumn(name = "OrderID", insertable = false, updatable = false)
    OrderEntity order;
}
