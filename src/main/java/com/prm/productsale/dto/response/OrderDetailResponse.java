package com.prm.productsale.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDetailResponse {

    private int orderId;
    private String orderStatus;
    private String pmMethod;
    private String bill;
    private LocalDateTime orderDate;

    private String address;
    private BigDecimal total;

    // Constructors
    public OrderDetailResponse() {
    }

    public OrderDetailResponse(int orderId, String orderStatus, String pmMethod, String bill, LocalDateTime orderDate, String address, BigDecimal total) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.pmMethod = pmMethod;
        this.bill = bill;
        this.orderDate = orderDate;
        this.address = address;
        this.total = total;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getPmMethod() {
        return pmMethod;
    }

    public String getBill() {
        return bill;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getTotal() {
        return total;
    }

    // Setters
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPmMethod(String pmMethod) {
        this.pmMethod = pmMethod;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}