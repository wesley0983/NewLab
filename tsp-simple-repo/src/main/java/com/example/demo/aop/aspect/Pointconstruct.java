package com.example.demo.aop.aspect;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Pointconstruct {
	 private static final Logger LOGGER = LoggerFactory.getLogger(Pointconstruct.class);
	@PostConstruct
	public void init(){
		LOGGER.info("xxxxxxxxxxxxxxxxxPostConstructTest");
	}
}
