package com.example.petshop;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductOrder {

    private Product product;
    private Order order;
    private int quantity;

    public ProductOrder(Product product, Order order, int quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }

    // Getters
    public Product getProduct() {
        return product;
    }

    @JsonIgnore
    public Order getOrder() {
        return order;
    }
    
    public int getQuantity() {
        return quantity;
    }
}
