package com.example.petshop.model;


import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "El nombre del producto es requerido")
    private String name;

    @Column(name = "price")
    @NotNull(message = "El precio del producto es requerido")
    @Min(value = 0, message = "El precio debe ser mayor a 0")
    private double price;
}