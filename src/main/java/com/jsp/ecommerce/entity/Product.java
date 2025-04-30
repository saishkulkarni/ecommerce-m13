package com.jsp.ecommerce.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.jsp.ecommerce.dto.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String category;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private Double price;
	@Column(nullable = false)
	private String imageUrl;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Column(nullable = false)
	private Integer stock;
	@UpdateTimestamp
	private LocalDateTime createdTime;
	
	private String reason;

	@ManyToOne
	Merchant merchant;
}
