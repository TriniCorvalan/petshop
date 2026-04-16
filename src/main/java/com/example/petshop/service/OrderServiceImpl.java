package com.example.petshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.example.petshop.model.Order;
import com.example.petshop.repository.OrderRepository;

public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }   

    @Override
    public List<Order> getOrdersByClientRut(String clientRut) {
        return orderRepository.findByClientRut(clientRut);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        order.setId(id);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
