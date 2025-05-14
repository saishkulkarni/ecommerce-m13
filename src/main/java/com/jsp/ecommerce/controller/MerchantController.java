package com.jsp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.ecommerce.dto.ProductDto;
import com.jsp.ecommerce.dto.UserDto;
import com.jsp.ecommerce.service.MerchantService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/merchant")
public class MerchantController {

	@Autowired
	MerchantService merchantService;

	@GetMapping("/register")
	public String loadRegister(UserDto userDto, Model model) {
		return merchantService.register(userDto, model);
	}

	@PostMapping("/register")
	public String register(@Valid UserDto userDto, BindingResult result, HttpSession session) {
		return merchantService.register(userDto, result, session);
	}

	@GetMapping("/otp")
	public String loadOtp() {
		return "merchant-otp.html";
	}

	@PostMapping("/otp")
	public String submitOtp(@RequestParam("otp") int otp, HttpSession session) {
		return merchantService.sumbitOtp(otp, session);
	}

	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		return merchantService.loadHome(session);
	}

	@GetMapping("/add-product")
	public String loadAddProduct(ProductDto productDto, Model model, HttpSession session) {
		return merchantService.loadAddProduct(productDto, model, session);
	}

	@PostMapping("/add-product")
	public String addProduct(@Valid ProductDto productDto, BindingResult result, HttpSession session) {
		return merchantService.addProduct(productDto, result, session);
	}

	@GetMapping("/manage-products")
	public String manageProducts(HttpSession session, Model model) {
		return merchantService.manageProducts(session, model);
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
		return merchantService.editProduct(id, model, session);
	}

	@PostMapping("/update-product")
	public String updateProduct(@Valid ProductDto productDto, BindingResult result, Model model,
			@RequestParam("id") Long id, HttpSession session) {
		return merchantService.updateProduct(id, productDto, result, model, session);
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id, HttpSession session) {
		return merchantService.deleteById(id, session);
	}

	@GetMapping("/profile")
	public String manageProfile(HttpSession session, Model model) {
		return merchantService.manageProfile(session, model);
	}

	@PostMapping("/manage-profile")
	public String manageProfile(HttpSession session, @ModelAttribute UserDto dto) {
		return merchantService.manageProfile(session, dto);
	}
}
