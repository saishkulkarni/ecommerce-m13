package com.jsp.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Cart;
import com.jsp.ecommerce.entity.OrderItem;
import com.jsp.ecommerce.entity.Orders;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	List<OrderItem> findByCart(Cart cart);

	List<OrderItem> findByOrdersId(Long orderId);

}
