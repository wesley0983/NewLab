package com.example.demo.scheduler;

import static com.example.demo.constant.PropertiesConst.PropertiesKeyEL.DB_SCHEDULER_COUNT_BOOK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.service.BookService;

@Component
public class BookScheduler {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookScheduler.class);
	 @Autowired
	 private BookService bookService;
	 	@Scheduled(cron = DB_SCHEDULER_COUNT_BOOK)
	    public void getAllBookLength() {
	    bookService.getBookCount();
	    	LOGGER.info("There are "+bookService.getBookCount()+" books in the database.");
	    }
	    
	
	
}
