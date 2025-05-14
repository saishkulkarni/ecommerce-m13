package com.jsp.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Customer;
import com.jsp.ecommerce.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByCustomer(Customer customer);

}
