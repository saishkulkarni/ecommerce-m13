package com.jsp.ecommerce.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CloudinaryHelper {
	public String saveImage(MultipartFile image) {
		return "link";
	}
}
