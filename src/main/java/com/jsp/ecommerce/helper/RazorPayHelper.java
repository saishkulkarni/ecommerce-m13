package com.jsp.ecommerce.helper;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Component
public class RazorPayHelper {
	
	@Value("${razor-pay.api.key}")
	String key;
	@Value("${razor-pay.api.secret}")
	String secret;
	
	public String createPayment(double amount) {
		RazorpayClient razorpay=null;
		try {
			razorpay = new RazorpayClient(key, secret);
		} catch (RazorpayException e) {
			e.printStackTrace();
		}

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount",amount*100); 
		orderRequest.put("currency","INR");

		try {
			Order order = razorpay.orders.create(orderRequest);
			return order.get("id");
		} catch (RazorpayException e) {
			e.printStackTrace();
			return null;
		}
	}
}
