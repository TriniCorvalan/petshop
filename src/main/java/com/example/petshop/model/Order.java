package com.example.petshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "El RUT del cliente es requerido")
    @Pattern(regexp = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{1}$", message = "El RUT debe estar en formato 12.345.678-9")
    @Column(name = "client_rut")
    private String clientRut;

    @NotBlank(message = "La descripción de productos es requerida")
    @Lob
    @Column(name = "products")
    private String products;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_price")
    @NotNull(message = "El precio total es requerido")
    @Min(value = 0, message = "El precio total debe ser mayor o igual a 0")
    private Double totalPrice;

    public Order() {
    }
}
