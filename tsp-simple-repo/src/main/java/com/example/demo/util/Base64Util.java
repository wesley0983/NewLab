package com.example.demo.util;

import java.util.Base64;

public class Base64Util {
	//String做成base64編碼
	public static String stringToBase64String(String inputString) {
		return Base64.getEncoder().encodeToString(inputString.getBytes());
	}
	// byte[]做成base64編碼
	public static String bytesToBase64String(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}
	//印出byte長度
	public static String Base64ByteLength(String base64String) {				
		return String.valueOf(base64String.getBytes().length);
	}

}
