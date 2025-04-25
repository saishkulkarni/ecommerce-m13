package com.jsp.ecommerce.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.jsp.ecommerce.dto.ProductDto;
import com.jsp.ecommerce.dto.UserDto;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface MerchantService {

	String register(UserDto userDto, Model model);

	String register(UserDto userDto, BindingResult result, HttpSession session);

	String sumbitOtp(int otp, HttpSession session);

	String loadHome(HttpSession session);

	String loadAddProduct(ProductDto productDto, Model model, HttpSession session);

	String addProduct(@Valid ProductDto productDto, BindingResult result, HttpSession session);

}
