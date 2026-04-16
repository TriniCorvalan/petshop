package com.example.petshop.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

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

    @OneToMany(mappedBy = "order")
    private List<ProductOrder> productPerOrder;

    @Column(name = "status")
    @NotBlank(message = "El estado es requerido")
    private String status;

    @Column(name = "total_price")
    @NotNull(message = "El precio total es requerido")
    @Min(value = 0, message = "El precio total debe ser mayor a 0")
    private double totalPrice;

    @PrePersist
    @PreUpdate
    private void computeTotalPrice() {
        this.totalPrice = (productPerOrder == null) ? 0.0
                : productPerOrder.stream()
                        .filter(productOrder -> productOrder.getProduct() != null)
                        .mapToDouble(productOrder -> productOrder.getProduct().getPrice() * productOrder.getQuantity())
                        .sum();
    }

    public Order() {
    }
}
