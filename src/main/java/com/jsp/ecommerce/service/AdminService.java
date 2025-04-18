package com.jsp.ecommerce.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.jsp.ecommerce.dto.UserDto;

public interface AdminService {

	String register(UserDto userDto, Model model);

	String register(UserDto userDto, BindingResult result);

}
