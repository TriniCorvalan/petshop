package com.example.petshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClientRut(String clientRut);
    
}
