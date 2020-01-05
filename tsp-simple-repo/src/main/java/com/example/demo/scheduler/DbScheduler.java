package com.example.demo.scheduler;


import com.example.demo.service.SchedulerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.example.demo.constant.PropertiesConst.PropertiesKeyEL.DB_SCHEDULER_CLEAN_UP_LOGOUT_TOKEN_CRON;

@Component
public class DbScheduler {
	private static final Logger LOGGER = LoggerFactory.getLogger(DbScheduler.class);
    @Autowired
    private SchedulerService schedulerService;
    
    @Scheduled(cron = DB_SCHEDULER_CLEAN_UP_LOGOUT_TOKEN_CRON)
    public void cleanUpLogoutToken() {
        schedulerService.cleanUpExpiredLogoutToken();
    }
   
    
    
    
    
    
    
}