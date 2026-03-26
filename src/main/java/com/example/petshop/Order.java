package com.example.petshop;

import java.util.List;

public class Order {
    private int id;
    private Client client;
    private List<ProductOrder> productPerOrder;
    private String status;

    // Constructor
    public Order(int id, Client client, List<ProductOrder> productPerOrder, String status) {
        this.id = id;
        this.client = client;
        this.productPerOrder = productPerOrder;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public List<ProductOrder> getProductPerOrder() {
        return productPerOrder;
    }

    public void setProductPerOrder(List<ProductOrder> productPerOrder) {
        this.productPerOrder = productPerOrder;
    }


    public String getStatus() {
        return status;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (ProductOrder productOrder : productPerOrder) {
            totalPrice += productOrder.getProduct().getPrice() * productOrder.getQuantity();
        }
        return totalPrice;
    }
}
