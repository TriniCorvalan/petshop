package com.example.petshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test   
    void testGettersAndSetters() {
        Order order = new Order();
        order.setId(1L);
        order.setClientRut("12345678-9");
        order.setProducts("Producto 1, Producto 2");
        order.setStatus("PENDIENTE");
        order.setTotalPrice(10000.0);
        
        assertEquals(1L, order.getId());
        assertEquals("12345678-9", order.getClientRut());
        assertEquals("Producto 1, Producto 2", order.getProducts());
        assertEquals("PENDIENTE", order.getStatus());
        assertEquals(10000.0, order.getTotalPrice());
    }
}
