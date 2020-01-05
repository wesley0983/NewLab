package com.example.demo.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.util.JsonUtil;

import static com.example.demo.constant.AOPConst.LOGGER_ORDER;
import static com.example.demo.constant.AOPConst.POINTCUT_CONTROLLER;
import static com.example.demo.constant.AOPConst.POINTCUT_SERVICELAYER;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
//控制載入順序
@Order(LOGGER_ORDER)
public class ControllerAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

	@Around(value = POINTCUT_CONTROLLER)
	public Object logAroundService(ProceedingJoinPoint jp) throws Throwable {
		// 方法開始
		String targetClassName = jp.getTarget().getClass().getSimpleName();
		String signatureName = jp.getSignature().getName();
		String methodName = String.format("%s：%s ", targetClassName, signatureName);
		String args = Arrays.stream(jp.getArgs()).filter(e -> !(e instanceof byte[]))
				.map(JsonUtil::convertObjectToJsonString).collect(Collectors.joining(", "));
		LOGGER.info("{}({}) 方法開始執行...", methodName, args);// 兩個括號 就是兩個參數
		Object result = jp.proceed();
		LOGGER.info("{}() 方法結束執行...", methodName);
		// 方法時間
		long beginTime = System.currentTimeMillis();
		long serviceCostTime = System.currentTimeMillis() - beginTime;

		LOGGER.info("方法花費時間: {}", serviceCostTime);
		return result;
	}


}
