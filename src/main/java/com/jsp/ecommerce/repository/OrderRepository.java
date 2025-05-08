package com.jsp.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
