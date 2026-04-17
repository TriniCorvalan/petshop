package com.example.petshop.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.example.petshop.model.Order;
import com.example.petshop.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

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
        order.setId(null);
        validateProducts(order);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
        validateProducts(order);
        existing.setClientRut(order.getClientRut());
        existing.setProducts(order.getProducts());
        existing.setStatus(order.getStatus());
        existing.setTotalPrice(order.getTotalPrice());
        return orderRepository.save(existing);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private void validateProducts(Order order) {
        if (order.getProducts() == null || order.getProducts().isEmpty()) {
            throw new IllegalArgumentException("La orden debe incluir al menos un producto");
        }
        for (String product : order.getProducts()) {
            if (product == null || product.isBlank()) {
                throw new IllegalArgumentException("Cada producto debe ser un texto no vacío");
            }
        }
    }
}
