package com.jsp.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Payment;

public interface PaymentRepsoitory extends JpaRepository<Payment, Long> {

}
