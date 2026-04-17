package com.example.petshop.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.petshop.model.*;

import com.example.petshop.service.OrderService;

@RestController
public class OrdersController {
    private List<Product> products = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orders;
    }

    @GetMapping("/orders/{clientRut}")
    public List<Order> getOrdersByClientRut(@PathVariable String clientRut) {
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getClientRut().equals(clientRut)) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }

    @PostMapping("/order")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/order/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/order/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
