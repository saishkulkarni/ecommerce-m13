package com.jsp.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jsp.ecommerce.dto.AdminDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/register")
	public String loadRegister(AdminDto adminDto, Model model) {
		model.addAttribute("adminDto", adminDto);
		return "admin-register.html";
	}

	@PostMapping("/register")
	public String register(@Valid AdminDto adminDto, BindingResult result) {
		if (result.hasErrors()) {
			return "admin-register.html";
		}
		return "redirect:/";
	}

}
