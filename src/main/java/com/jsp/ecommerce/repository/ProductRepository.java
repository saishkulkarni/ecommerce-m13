package com.jsp.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
