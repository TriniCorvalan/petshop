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

    public OrdersController() {
        products.add(buildProduct(1L, "Product 1", 100));
        products.add(buildProduct(2L, "Product 2", 200));
        products.add(buildProduct(3L, "Product 3", 300));
        products.add(buildProduct(4L, "Product 4", 400));
        products.add(buildProduct(5L, "Product 5", 500));
        products.add(buildProduct(6L, "Product 6", 600));
        products.add(buildProduct(7L, "Product 7", 700));
        products.add(buildProduct(8L, "Product 8", 800));
        products.add(buildProduct(9L, "Product 9", 900));
        products.add(buildProduct(10L, "Product 10", 1000));

        // Nota: aquí armamos las órdenes en memoria. Primero creamos la Order 
        // y luego asignamos su lista de ProductOrder
        orders.add(buildOrder(1L, "12.345.678-9", new int[] { 0 }, new int[] { 1 }, "COMPLETED")); // 1 producto
        orders.add(buildOrder(2L, "98.765.432-1", new int[] { 1, 2 }, new int[] { 2, 1 }, "PENDING")); // 2 productos
        orders.add(buildOrder(3L, "11.111.111-1", new int[] { 2, 3, 4 }, new int[] { 3, 2, 1 }, "COMPLETED")); // 3 productos
        orders.add(buildOrder(4L, "12.345.678-9", new int[] { 3 }, new int[] { 4 }, "PENDING")); // 1 producto
        orders.add(buildOrder(5L, "98.765.432-1", new int[] { 4, 5 }, new int[] { 5, 1 }, "COMPLETED")); // 2 productos
        orders.add(buildOrder(6L, "11.111.111-1", new int[] { 5, 6, 7 }, new int[] { 2, 3, 1 }, "PENDING")); // 3 productos
        orders.add(buildOrder(7L, "98.765.432-1", new int[] { 6 }, new int[] { 7 }, "COMPLETED")); // 1 producto
        orders.add(buildOrder(8L, "11.111.111-1", new int[] { 7, 8 }, new int[] { 8, 1 }, "PENDING")); // 2 productos
        orders.add(buildOrder(9L, "98.765.432-1", new int[] { 8 }, new int[] { 9 }, "COMPLETED")); // 1 producto
        orders.add(buildOrder(10L, "11.111.111-1", new int[] { 9, 0 }, new int[] { 10, 2 }, "PENDING")); // 2 productos

    }

    private Product buildProduct(Long id, String name, double price) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        return product;
    }

    private Order buildOrder(Long id, String clientRut, int[] productIndices, int[] quantities, String status) {
        Order order = new Order();
        order.setId(id);
        order.setClientRut(clientRut);
        order.setStatus(status);

        List<ProductOrder> productOrders = new ArrayList<>();
        for (int i = 0; i < productIndices.length; i++) {
            Product product = products.get(productIndices[i]);
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

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/orders/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
