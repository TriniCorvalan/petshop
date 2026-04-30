package com.example.petshop.controller;

import com.example.petshop.model.Order;
import com.example.petshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrdersControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrdersController ordersController;

    private MockMvc mockMvc;
    private Order baseOrder;
    private String validOrderJson;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ordersController).build();

        baseOrder = new Order();
        baseOrder.setId(1L);
        baseOrder.setClientRut("12.345.678-9");
        baseOrder.setProducts("Producto 1");
        baseOrder.setStatus("PENDIENTE");
        baseOrder.setTotalPrice(10000.0);

        validOrderJson = """
                {
                  "clientRut": "12.345.678-9",
                  "products": "Producto 1",
                  "status": "PENDIENTE",
                  "totalPrice": 10000.0
                }
                """;
    }

    @Test
    @DisplayName("GET /orders retorna lista de ordenes")
    void getOrders_shouldReturnOkAndOrdersList() throws Exception {
        when(orderService.getOrders()).thenReturn(List.of(baseOrder));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].clientRut").value("12.345.678-9"))
                .andExpect(jsonPath("$[0].products").value("Producto 1"))
                .andExpect(jsonPath("$[0].status").value("PENDIENTE"))
                .andExpect(jsonPath("$[0].totalPrice").value(10000.0));
    }

    @Test
    @DisplayName("GET /orders/{clientRut} retorna ordenes por RUT")
    void getOrdersByClientRut_shouldReturnOkAndFilteredList() throws Exception {
        when(orderService.getOrdersByClientRut("12.345.678-9")).thenReturn(List.of(baseOrder));

        mockMvc.perform(get("/orders/{clientRut}", "12.345.678-9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientRut").value("12.345.678-9"));
    }

    @Test
    @DisplayName("POST /order con payload valido crea orden")
    void createOrder_shouldReturnOkAndCreatedOrder() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(baseOrder);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validOrderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.clientRut").value("12.345.678-9"))
                .andExpect(jsonPath("$.products").value("Producto 1"))
                .andExpect(jsonPath("$.status").value("PENDIENTE"))
                .andExpect(jsonPath("$.totalPrice").value(10000.0));
    }

    @Test
    @DisplayName("POST /order con RUT invalido retorna 400")
    void createOrder_withInvalidRut_shouldReturnBadRequest() throws Exception {
        String invalidOrderJson = """
                {
                  "clientRut": "12345678-9",
                  "products": "Producto 1",
                  "status": "PENDIENTE",
                  "totalPrice": 10000.0
                }
                """;

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOrderJson))
                .andExpect(status().isBadRequest());

        verify(orderService, never()).createOrder(any(Order.class));
    }

    @Test
    @DisplayName("PUT /order/{id} actualiza orden")
    void updateOrder_shouldReturnOkAndUpdatedOrder() throws Exception {
        Order updatedOrder = new Order();
        updatedOrder.setId(1L);
        updatedOrder.setClientRut("12.345.678-9");
        updatedOrder.setProducts("Producto actualizado");
        updatedOrder.setStatus("PAGADA");
        updatedOrder.setTotalPrice(15000.0);

        String updateOrderJson = """
                {
                  "clientRut": "12.345.678-9",
                  "products": "Producto actualizado",
                  "status": "PAGADA",
                  "totalPrice": 15000.0
                }
                """;

        when(orderService.updateOrder(eq(1L), any(Order.class))).thenReturn(updatedOrder);

        mockMvc.perform(put("/order/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateOrderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PAGADA"))
                .andExpect(jsonPath("$.products").value("Producto actualizado"))
                .andExpect(jsonPath("$.totalPrice").value(15000.0));
    }

    @Test
    @DisplayName("DELETE /order/{id} elimina orden")
    void deleteOrder_shouldReturnOk() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/order/{id}", 1L))
                .andExpect(status().isOk());

        verify(orderService).deleteOrder(1L);
    }
}
