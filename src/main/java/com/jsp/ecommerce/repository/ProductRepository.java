package com.jsp.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.dto.Status;
import com.jsp.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByMerchant_id(Long id);

	List<Product> findByStatus(Status approved, Sort sort);

	List<Product> findByStatusAndCategory(Status approved, String category, Sort ascending);

	List<Product> findByStatusAndNameLike(Status approved, String string, Sort ascending);

}
