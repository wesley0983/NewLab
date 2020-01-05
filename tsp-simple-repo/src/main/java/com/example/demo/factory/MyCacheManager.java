package com.example.demo.factory;

import static com.example.demo.constant.CacheConst.CACHE_NAME_BOOK;
import static com.example.demo.constant.CacheConst.CACHE_GET_ALL;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.cache.Cache;
import org.springframework.util.StringUtils;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.BookUpdateDTO;

import com.example.demo.util.DateTimeUtil;
import static com.example.demo.constant.ControllerConst.AUTHOR;
import static com.example.demo.constant.ControllerConst.NAME;
import static com.example.demo.constant.ControllerConst.PUBLICATIONDATE;



public class MyCacheManager {

	public static List<BookDTO> editBookCache(BookUpdateDTO book, Cache cache) {
		List<BookDTO> allCache = (List<BookDTO>) cache.get(CACHE_GET_ALL).get();
		
		Predicate<BookDTO> equalsname = p->p.getName().equals(book.getName());
		
		allCache.stream().forEach(bookDTO->{
		if (equalsname.test(bookDTO)) {
			if (StringUtils.hasText(book.getName()))
			bookDTO.setName(book.getName());
			if (StringUtils.hasText(book.getAuthor()))
			bookDTO.setAuthor(book.getAuthor());
			if (StringUtils.hasText(book.getPublicationDate()))
			bookDTO.setPublicationDate(DateTimeUtil.stringToDate(book.getPublicationDate()).toString());
		}
		});
		
		return allCache;
		
	}
	
	public static List<BookDTO> removeBookCache(String name, Cache cache) {
		List<BookDTO> allCache = (List<BookDTO>) cache.get(CACHE_GET_ALL).get();
			return allCache.stream()
					.filter(e->!e.getName().equals(name))
					.collect(toList());
	}
	
	public static List<BookDTO> sortBookCache(String order,List<BookDTO> bookDTOlist){
	switch(order) {
		
		case AUTHOR:
			Comparator<BookDTO> byBookAuthor = comparing(BookDTO::getAuthor);	
			return bookDTOlist.stream()
			  .sorted(byBookAuthor)
			  .collect(toList());
		case NAME:		
			Comparator<BookDTO> compByLength = (aName, bName) -> aName.getName().length() - bName.getName().length();
			return bookDTOlist.stream()
							  .sorted(compByLength.reversed())
							  .collect(toList());
			
		case PUBLICATIONDATE:			
			Comparator<BookDTO> byBookPublicationDate = comparing(BookDTO::getPublicationDate);
			return bookDTOlist.stream()
					   		  .sorted(byBookPublicationDate.reversed())
					   		  .collect(toList());
		default:
			return bookDTOlist.stream()
							  .collect(toList());
		}
	}
	

}
