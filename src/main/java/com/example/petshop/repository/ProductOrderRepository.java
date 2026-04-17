package com.example.petshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petshop.model.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    
}
