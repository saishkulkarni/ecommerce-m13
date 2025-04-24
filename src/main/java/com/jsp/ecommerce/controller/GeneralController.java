package com.jsp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.ecommerce.service.GeneralService;

import jakarta.servlet.http.HttpSession;

@Controller
public class GeneralController {

	@Autowired
	GeneralService generalService;

	@GetMapping("/login")
	public String loadLogin() {
		return "login.html";
	}

	@PostMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session) {
		return generalService.login(email, password, session);
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		return generalService.logout(session);
	}
}
