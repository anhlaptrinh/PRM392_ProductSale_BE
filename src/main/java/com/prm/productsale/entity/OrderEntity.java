package com.prm.productsale.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity(name = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    int id;
    @ManyToOne
    @JoinColumn(name = "CartID")
    CartEntity cart;
    @ManyToOne
    @JoinColumn(name = "UserID")
    UserEntity user;
    @Column(name = "PaymentMethod")
    String pmMethod;
    @Column(name = "BillingAddress")
    String bill;
    @Column(name = "OrderStatus")
    String orderStatus;
    @Column(name = "OrderDate")
    LocalDateTime orderDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getPmMethod() {
        return pmMethod;
    }

    public void setPmMethod(String pmMethod) {
        this.pmMethod = pmMethod;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
