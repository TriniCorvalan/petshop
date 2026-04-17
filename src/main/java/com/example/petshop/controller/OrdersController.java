package com.example.petshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.petshop.model.Order;
import com.example.petshop.service.OrderService;

import jakarta.validation.Valid;

@RestController
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/orders/{clientRut}")
    public List<Order> getOrdersByClientRut(@PathVariable String clientRut) {
        return orderService.getOrdersByClientRut(clientRut);
    }

    @PostMapping("/order")
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/order/{id}")
    public Order updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/order/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
