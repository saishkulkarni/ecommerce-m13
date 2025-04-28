package com.jsp.ecommerce.helper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
public class CloudinaryHelper {

	@Value("${cloudinary.api.name}")
	private String name;
	@Value("${cloudinary.api.key}")
	private String key;
	@Value("${cloudinary.api.secret}")
	private String secret;

	public String saveImage(MultipartFile image) {
		String url = "cloudinary://" + key + ":" + secret + "@" + name;

		Cloudinary cloudinary = new Cloudinary(url);

		try {
			byte[] picture = new byte[image.getInputStream().available()];
			image.getInputStream().read(picture);

			return (String) cloudinary.uploader().upload(picture, ObjectUtils.asMap("folder", "ecommerce")).get("url");

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
