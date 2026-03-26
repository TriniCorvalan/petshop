package com.example.petshop;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {
    private List<Client> clients = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public OrdersController() {
        clients.add(new Client(1, "Juanito Perez", "juanito.perez@example.com", "1234567890", "Calle 123, Ciudad, Pais"));
        clients.add(new Client(2, "Maria Garcia", "maria.garcia@example.com", "1234567890", "Calle 456, Ciudad, Pais"));
        products.add(new Product(1, "Product 1", 100));
        products.add(new Product(2, "Product 2", 200));
        products.add(new Product(3, "Product 3", 300));
        products.add(new Product(4, "Product 4", 400));
        products.add(new Product(5, "Product 5", 500));
        products.add(new Product(6, "Product 6", 600));
        products.add(new Product(7, "Product 7", 700));
        products.add(new Product(8, "Product 8", 800));
        products.add(new Product(9, "Product 9", 900));
        products.add(new Product(10, "Product 10", 1000));

        // Nota: aquí armamos las órdenes en memoria. Primero creamos la Order 
        // y luego asignamos su lista de ProductOrder
        orders.add(buildOrder(1, clients.get(0), new int[] { 0 }, new int[] { 1 }, "COMPLETED")); // 1 producto
        orders.add(buildOrder(2, clients.get(0), new int[] { 1, 2 }, new int[] { 2, 1 }, "PENDING")); // 2 productos
        orders.add(buildOrder(3, clients.get(1), new int[] { 2, 3, 4 }, new int[] { 3, 2, 1 }, "COMPLETED")); // 3 productos
        orders.add(buildOrder(4, clients.get(1), new int[] { 3 }, new int[] { 4 }, "PENDING")); // 1 producto
        orders.add(buildOrder(5, clients.get(0), new int[] { 4, 5 }, new int[] { 5, 1 }, "COMPLETED")); // 2 productos
        orders.add(buildOrder(6, clients.get(0), new int[] { 5, 6, 7 }, new int[] { 2, 3, 1 }, "PENDING")); // 3 productos
        orders.add(buildOrder(7, clients.get(1), new int[] { 6 }, new int[] { 7 }, "COMPLETED")); // 1 producto
        orders.add(buildOrder(8, clients.get(1), new int[] { 7, 8 }, new int[] { 8, 1 }, "PENDING")); // 2 productos
        orders.add(buildOrder(9, clients.get(0), new int[] { 8 }, new int[] { 9 }, "COMPLETED")); // 1 producto
        orders.add(buildOrder(10, clients.get(1), new int[] { 9, 0 }, new int[] { 10, 2 }, "PENDING")); // 2 productos

    }

    private Order buildOrder(int id, Client client, int[] productIndices, int[] quantities, String status) {
        Order order = new Order(id, client, new ArrayList<ProductOrder>(), status);

        List<ProductOrder> productOrders = new ArrayList<>();
        for (int i = 0; i < productIndices.length; i++) {
            Product product = products.get(productIndices[i]);
            int quantity = quantities[i];
            productOrders.add(new ProductOrder(product, order, quantity));
        }

        order.setProductPerOrder(productOrders);
        return order;
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orders;
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    @GetMapping("/orders/client/{clientId}")
    public List<Order> getOrdersByClientId(@PathVariable int clientId) {
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getClient().getId() == clientId) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }

    @GetMapping("/orders/{orderId}/products")
    public List<ProductOrder> getProductsByOrderId(@PathVariable int orderId) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return order.getProductPerOrder ();
            }
        }
        return null;
    }
}
