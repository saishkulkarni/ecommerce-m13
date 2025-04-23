package com.jsp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.ecommerce.dto.UserDto;
import com.jsp.ecommerce.service.AdminService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@GetMapping("/register")
	public String loadRegister(UserDto userDto, Model model) {
		return adminService.register(userDto, model);
	}

	@PostMapping("/register")
	public String register(@Valid UserDto userDto, BindingResult result, HttpSession session) {
		return adminService.register(userDto, result, session);
	}

	@GetMapping("/otp")
	public String loadOtp() {
		return "admin-otp.html";
	}

	@PostMapping("/otp")
	public String submitOtp(@RequestParam("otp") int otp, HttpSession session) {
		return adminService.sumbitOtp(otp, session);
	}
	
	@GetMapping("/home")
	public String loadHome() {
		return "admin-home.html";
	}
}
