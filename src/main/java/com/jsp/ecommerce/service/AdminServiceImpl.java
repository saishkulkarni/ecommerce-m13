package com.jsp.ecommerce.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.jsp.ecommerce.dto.OrderStatus;
import com.jsp.ecommerce.dto.Status;
import com.jsp.ecommerce.dto.UserDto;
import com.jsp.ecommerce.entity.Admin;
import com.jsp.ecommerce.entity.Orders;
import com.jsp.ecommerce.entity.Product;
import com.jsp.ecommerce.helper.AES;
import com.jsp.ecommerce.helper.EmailSender;
import com.jsp.ecommerce.repository.AdminRepository;
import com.jsp.ecommerce.repository.CustomerRepository;
import com.jsp.ecommerce.repository.MerchantRepository;
import com.jsp.ecommerce.repository.OrderRepository;
import com.jsp.ecommerce.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	MerchantRepository merchantRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	EmailSender emailSender;
	@Autowired
	OrderRepository orderRepository;

	@Override
	public String register(UserDto userDto, Model model) {
		model.addAttribute("userDto", userDto);
		return "admin-register.html";
	}

	@Override
	public String register(UserDto userDto, BindingResult result, HttpSession session) {
		if (!userDto.getPassword().equals(userDto.getConfirmPassword()))
			result.rejectValue("confirmPassword", "error.confirmPassword",
					"* Password and Confirm password not matching");
		if (adminRepository.existsByEmail(userDto.getEmail()) || customerRepository.existsByEmail(userDto.getEmail())
				|| merchantRepository.existsByEmail(userDto.getEmail()))
			result.rejectValue("email", "error.email", "* Email Already Exists");

		if (result.hasErrors()) {
			return "admin-register.html";
		}

		int otp = new Random().nextInt(100000, 1000000);
		emailSender.sendEmail(userDto, otp);

		session.setAttribute("otp", otp);
		session.setAttribute("userDto", userDto);
		session.setAttribute("pass", "Otp Sent Success");
		return "redirect:/admin/otp";
	}

	@Override
	public String sumbitOtp(int otp, HttpSession session) {
		int generatedOtp = (int) session.getAttribute("otp");
		if (generatedOtp == otp) {
			UserDto dto = (UserDto) session.getAttribute("userDto");
			Admin admin = new Admin();
			admin.setEmail(dto.getEmail());
			admin.setName(dto.getName());
			admin.setPassword(AES.encrypt(dto.getPassword()));
			adminRepository.save(admin);
			session.setAttribute("pass", "Account Created Success");
			session.removeAttribute("otp");
			session.removeAttribute("userDto");
			return "redirect:/";
		} else {
			session.setAttribute("fail", "Otp Missmatch");
			return "redirect:/admin/otp";
		}

	}

	@Override
	public String loadHome(HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null)
			return "admin-home.html";
		else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}

	@Override
	public String viewProducts(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			List<Product> products = productRepository.findAll();
			if (products.isEmpty()) {
				session.setAttribute("fail", "No Products Present Yet");
				return "redirect:/admin/home";
			} else {
				model.addAttribute("products", products);
				return "admin-products.html";
			}
		} else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}

	@Override
	public String approveProduct(Long id, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			Product product = productRepository.findById(id).orElseThrow();
			product.setStatus(Status.APPROVED);
			productRepository.save(product);
			session.setAttribute("pass", "Status Updated Success");
			return "redirect:/admin/products";
		} else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}

	@Override
	public String rejectProduct(Long id,Model model, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			model.addAttribute("id", id);
			return "reason.html";
		} else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}

	@Override
	public String rejectProduct(Long id, String reason, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			Product product = productRepository.findById(id).orElseThrow();
			product.setStatus(Status.REJECTED);
			product.setReason(reason);
			productRepository.save(product);
			session.setAttribute("fail", "Status Updated Success");
			return "redirect:/admin/products";
		} else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}

	@Override
	public String loadOrders(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			List<Orders> orders = orderRepository.findAll();
			if (orders.isEmpty()) {
				session.setAttribute("fail", "No Orders Present Yet");
				return "redirect:/admin/home";
			} else {
				model.addAttribute("orders", orders);
				return "admin-orders.html";
			}
		} else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}
	
	@Override
	public String updateStatus(Long orderId, String status, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			Orders order = orderRepository.findById(orderId).orElseThrow();
			order.setOrderStatus(OrderStatus.valueOf(status));
			orderRepository.save(order);
			session.setAttribute("pass", "Status Updated Success");
			return "redirect:/admin/manage-orders";
		} else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}	
	}

	@Override
	public String loadOverView(HttpSession session,Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null){
			int totalOrders = orderRepository.findAll().size();
   			int totalMerchants = merchantRepository.findAll().size();
   			int totalCustomers = customerRepository.findAll().size();
   			int totalProducts = productRepository.findAll().size();
   			double totalRevenue = orderRepository.findAll().stream()
   					.filter(order -> order.getOrderStatus() == OrderStatus.DELIVERED)
   					.mapToDouble(order -> order.getTotalAmount())
   					.sum();
   			
   			model.addAttribute("totalOrders", totalOrders);
   			model.addAttribute("totalMerchants", totalMerchants); 
   			model.addAttribute("totalCustomers", totalCustomers);
   			model.addAttribute("totalProducts", totalProducts);
   			model.addAttribute("totalRevenue", totalRevenue);

			return "admin-overview.html";
		}
		else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}
}
