package com.jsp.ecommerce.helper;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.jsp.ecommerce.dto.UserDto;

@Component
public class EmailSender {

	@Autowired
	JavaMailSender mailSender;

	private int generateOTP() {
		return new Random().nextInt(100000, 1000000);
	}

	public void sendEmail(UserDto userDto) {
		int otp = generateOTP();
		try {
			
		} catch (Exception e) {
			System.err.println("OTP Sending to Email Failed but the otp is : " + otp);
		}
	}
}
