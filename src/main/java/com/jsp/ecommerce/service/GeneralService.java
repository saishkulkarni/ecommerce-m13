package com.jsp.ecommerce.service;

import jakarta.servlet.http.HttpSession;

public interface GeneralService {

	String login(String email, String password, HttpSession session);

	String logout(HttpSession session);

}
