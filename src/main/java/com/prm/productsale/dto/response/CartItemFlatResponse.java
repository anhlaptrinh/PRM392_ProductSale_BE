package com.prm.productsale.dto.response;

import java.math.BigDecimal;

public class CartItemFlatResponse {

    private int id;
    private int quantity;
    private BigDecimal price;
    private int productId;
    private String productName;
    private String imageURL;

    public CartItemFlatResponse() {
    }

    public CartItemFlatResponse(int id, int quantity, BigDecimal price, int productId, String productName, String imageURL) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
        this.productName = productName;
        this.imageURL = imageURL;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getImageURL() {
        return imageURL;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
