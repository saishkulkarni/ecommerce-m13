package com.jsp.ecommerce.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.jsp.ecommerce.dto.ProductDto;
import com.jsp.ecommerce.dto.Status;
import com.jsp.ecommerce.dto.UserDto;
import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.Product;
import com.jsp.ecommerce.helper.AES;
import com.jsp.ecommerce.helper.CloudinaryHelper;
import com.jsp.ecommerce.helper.EmailSender;
import com.jsp.ecommerce.repository.AdminRepository;
import com.jsp.ecommerce.repository.CustomerRepository;
import com.jsp.ecommerce.repository.MerchantRepository;
import com.jsp.ecommerce.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service
public class MerchantServiceImpl implements MerchantService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	MerchantRepository merchantRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CloudinaryHelper cloudinaryHelper;
	@Autowired
	EmailSender emailSender;

	@Override
	public String register(UserDto userDto, Model model) {
		model.addAttribute("userDto", userDto);
		return "merchant-register.html";
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
			return "merchant-register.html";
		}

		int otp = new Random().nextInt(100000, 1000000);
		emailSender.sendEmail(userDto, otp);

		session.setAttribute("otp", otp);
		session.setAttribute("userDto", userDto);
		session.setAttribute("pass", "Otp Sent Success");

		return "redirect:/merchant/otp";
	}

	@Override
	public String sumbitOtp(int otp, HttpSession session) {
		int generatedOtp = (int) session.getAttribute("otp");
		if (generatedOtp == otp) {
			UserDto dto = (UserDto) session.getAttribute("userDto");
			Merchant merchant = new Merchant();
			merchant.setEmail(dto.getEmail());
			merchant.setName(dto.getName());
			merchant.setPassword(AES.encrypt(dto.getPassword()));
			merchantRepository.save(merchant);
			session.setAttribute("pass", "Account Created Success");
			session.removeAttribute("otp");
			session.removeAttribute("userDto");
			return "redirect:/";
		} else {
			session.setAttribute("fail", "Otp Missmatch");
			return "redirect:/merchant/otp";
		}
	}

	@Override
	public String loadHome(HttpSession session) {
		Merchant merchant = (Merchant) session.getAttribute("merchant");
		if (merchant != null)
			return "merchant-home.html";
		else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}

	@Override
	public String loadAddProduct(ProductDto productDto, Model model, HttpSession session) {
		Merchant merchant = (Merchant) session.getAttribute("merchant");
		if (merchant != null) {
			model.addAttribute("productDto", productDto);
			return "add-product.html";
		} else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}

	@Override
	public String addProduct(@Valid ProductDto productDto, BindingResult result, HttpSession session) {
		Merchant merchant = (Merchant) session.getAttribute("merchant");
		if (merchant != null) {
			if(productDto.getImage().isEmpty())
				result.rejectValue("image","error.image","* Select One Image");
			if (result.hasErrors())
				return "add-product.html";
			else {
				Product product=new Product();
				product.setName(productDto.getName());
				product.setDescription(productDto.getDescription());
				product.setCategory(productDto.getCategory());
				product.setStock(productDto.getStock());
				product.setPrice(productDto.getPrice());
				product.setImageUrl(cloudinaryHelper.saveImage(productDto.getImage()));
				product.setMerchant(merchant);
				product.setStatus(Status.PENDING);
				
				productRepository.save(product);
				session.setAttribute("pass", "Product Added Success");
				return "redirect:/merchant/home";
			}
		} else {
			session.setAttribute("fail", "Invalid Session, First Login to Access");
			return "redirect:/login";
		}
	}

}
