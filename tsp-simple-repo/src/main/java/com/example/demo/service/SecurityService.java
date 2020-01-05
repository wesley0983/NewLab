package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.util.Base64Util;

@Service
public class SecurityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);
	
	//String做成base64編碼
	public String stringToBase64String(String inputString) {
		return Base64Util.stringToBase64String(inputString);
	}
	// byte[]做成base64編碼
	public String byteToBase64String (byte[] inputbyte) {
		return Base64Util.bytesToBase64String(inputbyte);
	}
	// 解碼印出byte長度
	public void Base64StringLength(String base64String) {
	
		LOGGER.info("byte陣列長度  =  "+Base64Util.Base64ByteLength(base64String));
		try {
		LOGGER.info("Base64解碼  =   "+new String(Base64.getDecoder().decode(base64String), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		LOGGER.info("Base64解碼失敗"+e);
		}
	}

}
