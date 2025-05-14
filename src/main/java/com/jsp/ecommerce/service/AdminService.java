package com.jsp.ecommerce.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.jsp.ecommerce.dto.UserDto;

import jakarta.servlet.http.HttpSession;

public interface AdminService {

	String register(UserDto userDto, Model model);

	String register(UserDto userDto, BindingResult result, HttpSession session);

	String sumbitOtp(int otp, HttpSession session);

	String loadHome(HttpSession session);

	String viewProducts(HttpSession session, Model model);

	String approveProduct(Long id, HttpSession session);

	String rejectProduct(Long id,Model model, HttpSession session);

	String rejectProduct(Long id, String reason, HttpSession session);

    String loadOrders(HttpSession session, Model model);

    String updateStatus(Long orderId, String status, HttpSession session);

    String loadOverView(HttpSession session,Model model);

}
