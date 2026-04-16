package com.example.petshop.service;

import java.util.List;

import com.example.petshop.model.Order;

public interface OrderService {
    List<Order> getOrders();
    List<Order> getOrdersByClientRut(String clientRut);
    Order createOrder(Order order);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
}
