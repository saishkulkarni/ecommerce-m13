package com.jsp.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Cart;
import com.jsp.ecommerce.entity.Customer;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByCustomer(Customer customer);

}
