package com.example.petshop.model;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @NotEmpty(message = "La orden debe incluir al menos un producto")
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "products")
    private List<@NotBlank(message = "Cada nombre de producto es requerido") String> products;

    @Column(name = "status")
    @NotBlank(message = "El estado es requerido")
    private String status;

    @Column(name = "total_price")
    @NotNull(message = "El precio total es requerido")
    @Min(value = 0, message = "El precio total debe ser mayor o igual a 0")
    private Double totalPrice;

    public Order() {
    }
}
