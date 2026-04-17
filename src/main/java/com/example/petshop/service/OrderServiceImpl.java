package com.example.petshop.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import com.example.petshop.model.*;
import com.example.petshop.repository.*;


public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

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
        if (order.getProductPerOrder() == null || order.getProductPerOrder().isEmpty()) {
            throw new IllegalArgumentException("La orden debe incluir al menos un producto");
        }
        List<ProductOrder> lines = order.getProductPerOrder();
        Long[] productIds = new Long[lines.size()];
        int[] quantities = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            ProductOrder line = lines.get(i);
            if (line.getProduct() == null || line.getProduct().getId() == null) {
                throw new IllegalArgumentException("Cada línea debe incluir el id del producto");
            }
            productIds[i] = line.getProduct().getId();
            quantities[i] = line.getQuantity();
        }
        Order built = buildOrder(order.getClientRut(), productIds, quantities, order.getStatus());
        return orderRepository.save(built);
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

    private Order buildOrder(String clientRut, Long[] productIds, int[] quantities, String status) {
        Order order = new Order();
        order.setClientRut(clientRut);
        order.setStatus(status);

        List<ProductOrder> productOrders = new ArrayList<>();
        for (int i = 0; i < productIds.length; i++) {
            Product product = productRepository.findById(productIds[i])
                .orElseThrow(() -> new RuntimeException("Product not found"));
            int quantity = quantities[i];
            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(product);
            productOrder.setOrder(order);
            productOrder.setQuantity(quantity);
            productOrders.add(productOrder);
        }

        order.setProductPerOrder(productOrders);
        order.setTotalPrice(productOrders.stream()
                .mapToDouble(productOrder -> productOrder.getProduct().getPrice() * productOrder.getQuantity())
                .sum());
        return order;
    }
}
