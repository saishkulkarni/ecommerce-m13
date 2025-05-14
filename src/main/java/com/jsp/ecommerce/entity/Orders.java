package com.jsp.ecommerce.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.jsp.ecommerce.dto.OrderStatus;
import com.jsp.ecommerce.dto.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Orders {
	@Id
	@GeneratedValue(generator = "orderId")
	@SequenceGenerator(initialValue = 101001,allocationSize = 1,name = "orderId")
	private Long id;
	@Column(nullable = false)
	private Double totalAmount;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus paymentStatus;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus orderStatus;
	@CreationTimestamp
	private LocalDateTime creationTime;
	@Column(nullable = false)
	private Long mobile;
	@Column(nullable = false)
	private String address;
	
	@ManyToOne
	private Customer customer;
}
