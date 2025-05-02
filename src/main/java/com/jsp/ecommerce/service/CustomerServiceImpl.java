package com.jsp.ecommerce.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.jsp.ecommerce.dto.Status;
import com.jsp.ecommerce.dto.UserDto;
import com.jsp.ecommerce.entity.Customer;
import com.jsp.ecommerce.entity.Product;
import com.jsp.ecommerce.helper.AES;
import com.jsp.ecommerce.helper.EmailSender;
import com.jsp.ecommerce.repository.AdminRepository;
import com.jsp.ecommerce.repository.CustomerRepository;
import com.jsp.ecommerce.repository.MerchantRepository;
import com.jsp.ecommerce.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	MerchantRepository merchantRepository;
	@Autowired
	EmailSender emailSender;
	@Autowired
	ProductRepository productRepository;

	@Override
	public String register(UserDto userDto, Model model) {
		model.addAttribute("userDto", userDto);
		return "customer-register.html";
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
			return "customer-register.html";
		}

		int otp = new Random().nextInt(100000, 1000000);
		emailSender.sendEmail(userDto, otp);

		session.setAttribute("otp", otp);
		session.setAttribute("userDto", userDto);
		session.setAttribute("pass", "Otp Sent Success");

		return "redirect:/customer/otp";
	}

	@Override
	public String sumbitOtp(int otp, HttpSession session) {
		int generatedOtp = (int) session.getAttribute("otp");
		if (generatedOtp == otp) {
			UserDto dto = (UserDto) session.getAttribute("userDto");
			Customer customer = new Customer();
			customer.setEmail(dto.getEmail());
			customer.setName(dto.getName());
			customer.setPassword(AES.encrypt(dto.getPassword()));
			customerRepository.save(customer);
			session.setAttribute("pass", "Account Created Success");
			session.removeAttribute("otp");
			session.removeAttribute("userDto");
			return "redirect:/";
		} else {
			session.setAttribute("fail", "Otp Missmatch");
			return "redirect:/customer/otp";
		}
	}

	@Override
	public String loadHome(HttpSession session) {
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer != null)
			return "customer-home.html";
		else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}

	@Override
	public String viewProducts(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer != null){
			List<Product> products=productRepository.findByStatus(Status.APPROVED);
			if(products.isEmpty()) {
				session.setAttribute("fail", "No Products Added Yet");
				return "redirect:/customer/home";
			}else {
				model.addAttribute("products", products);
				return "view-products.html";
			}
		}
		else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}
}
