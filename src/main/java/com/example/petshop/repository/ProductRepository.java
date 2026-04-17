package com.example.petshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
