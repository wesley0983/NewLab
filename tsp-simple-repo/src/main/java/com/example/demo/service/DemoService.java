package com.example.demo.service;

import com.example.demo.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.demo.constant.PropertiesConst.PropertiesKeyEL.RSA_PUBLIC_KEY;

@Service
public class DemoService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value(RSA_PUBLIC_KEY)
    private String rsaPublicKey;
    //rsa 加密方法
    public String encodePcode(String pcode) throws Exception { 	
    	System.out.println("xxxxx"+pcode);
        return CryptoUtil.rsaEncryptByPublicKey(pcode, rsaPublicKey);
    }
}
