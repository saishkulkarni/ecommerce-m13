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

import com.jsp.ecommerce.dto.UserDto;
import com.jsp.ecommerce.service.CustomerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@GetMapping("/register")
	public String loadRegister(UserDto userDto, Model model) {
		return customerService.register(userDto, model);
	}

	@PostMapping("/register")
	public String register(@Valid UserDto userDto, BindingResult result, HttpSession session) {
		return customerService.register(userDto, result, session);
	}

	@GetMapping("/otp")
	public String loadOtp() {
		return "customer-otp.html";
	}

	@PostMapping("/otp")
	public String submitOtp(@RequestParam("otp") int otp, HttpSession session) {
		return customerService.sumbitOtp(otp, session);
	}

	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		return customerService.loadHome(session);
	}

	@GetMapping("/products")
	public String viewProducts(HttpSession session, Model model,
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "sort", required = false) String sort,
			@RequestParam(name = "search", required = false) String search) {
		return customerService.viewProducts(session, model, category, sort, search);
	}

	@GetMapping("/add-cart/{id}")
	public String addToCart(@PathVariable("id") Long id, HttpSession session) {
		return customerService.addToCart(id, session);
	}

	@GetMapping("/cart")
	public String viewCart(HttpSession session, Model model) {
		return customerService.viewCart(session, model);
	}

	@GetMapping("/increase/{id}")
	public String increase(@PathVariable("id") Long id, HttpSession session) {
		return customerService.increaseQuantity(id, session);
	}

	@GetMapping("/decrease/{id}")
	public String decrease(@PathVariable("id") Long id, HttpSession session) {
		return customerService.decreaseQuantity(id, session);
	}

	@GetMapping("/payment")
	public String proceedPayment(HttpSession session, Model model) {
		return customerService.proceedPayment(session, model);
	}

	@PostMapping("/payment/{id}")
	public String confirmPayment(@PathVariable("id") Long id, @RequestParam("razorpay_payment_id") String paymentId,
			HttpSession session) {
		return customerService.confirmPament(id, paymentId, session);
	}

	@GetMapping("/manage-profile")
	public String manageProfile(HttpSession session, Model model) {
		return customerService.manageProfile(session, model);
	}

	@PostMapping("/manage-profile")
	public String manageProfile(HttpSession session, @ModelAttribute UserDto dto, @RequestParam("address") String address,
			@RequestParam("mobile") Long mobile) {
		return customerService.manageProfile(session, dto, mobile, address);
	}

	@GetMapping("/order-history")
	public String orderHistory(HttpSession session, Model model) {
		return customerService.orderHistory(session, model);
	}

	@GetMapping("/track-orders")
	public String trackOrders(HttpSession session) {
		return customerService.loadTrackOrder(session);
	}

	@PostMapping("/track-orders")
	public String trackOrders(@RequestParam Long orderId, HttpSession session, Model model) {
		return customerService.trackOrders(orderId, session, model);
	}
	
}
