package com.jsp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String loadHome(HttpSession session) {
		return adminService.loadHome(session);
	}

	@GetMapping("/products")
	public String loadProducts(HttpSession session, Model model) {
		return adminService.viewProducts(session, model);
	}

	@GetMapping("/approve/{id}")
	public String approveProduct(@PathVariable("id") Long id, HttpSession session) {
		return adminService.approveProduct(id, session);
	}
	
	@GetMapping("/reject/{id}")
	public String rejectProduct(@PathVariable("id") Long id,Model model, HttpSession session) {
		return adminService.rejectProduct(id,model, session);
	}
	
	@PostMapping("/reject/{id}")
	public String rejectProduct(@PathVariable("id") Long id,@RequestParam("reason") String reason, HttpSession session) {
		return adminService.rejectProduct(id,reason, session);
	}

	@GetMapping("/manage-orders")
	public String loadOrders(HttpSession session, Model model) {
		return adminService.loadOrders(session, model);
	}

	@PostMapping("/update-status")
	public String updateStatus(@RequestParam Long orderId,@RequestParam String status, HttpSession session) {
		return adminService.updateStatus(orderId, status, session);
	}

	@GetMapping("/overview")
	public String loadOverview(HttpSession session,Model model) {
		return adminService.loadOverView(session,model);
	}
}
