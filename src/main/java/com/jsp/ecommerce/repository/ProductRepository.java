package com.jsp.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByMerchant_id(Long id);

}
