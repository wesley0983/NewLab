package com.example.demo.service;

import static com.example.demo.constant.DemoResponseConst.ReturnCode.E0001;

import static java.util.stream.Collectors.toList;

import static java.util.Comparator.comparing;

import java.time.LocalDate;


import java.util.Comparator;

import java.util.List;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.StreamSupport;


import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.config.CacheConfig;
import com.example.demo.dao.BookDao;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.BookUpdateDTO;
import com.example.demo.error.DemoException;
import com.example.demo.factory.MyCacheManager;
import com.example.demo.po.BookPO;
import com.example.demo.util.DateTimeUtil;
import static com.example.demo.constant.CacheConst.CACHE_NAME_BOOK;
import static com.example.demo.constant.CacheConst.CACHE_GET_ALL;
import static com.example.demo.constant.CacheConst.CACHE_GET_ALL_KEY_NAME;
import static com.example.demo.constant.ControllerConst.AUTHOR;
import static com.example.demo.constant.ControllerConst.NAME;
import static com.example.demo.constant.ControllerConst.PUBLICATIONDATE;

@Service
public class BookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookDao bookDao;
	
	/*  不需要用到CacheManager cacheManager(太大)，同樣為@Bean自己設的bookCache就可以做到了
     *  @Qualifier(value = CACHE_NAME_BOOK_CACHE) 
     *  如果bookCache名稱與CacheConfig裡面的名稱public Cache bookCache() 不一樣的話，就需要用到@Qualifier去找Bean的名稱
     */
	@Autowired
	@Qualifier(value=CACHE_NAME_BOOK)
	Cache bookCache;
	

	// 取得全部書
	@Cacheable(cacheNames = CACHE_NAME_BOOK, key = CACHE_GET_ALL_KEY_NAME)
	public List<BookDTO> getAllBook() {
		if (bookCache.get(CACHE_GET_ALL)!= null) {
			List<BookDTO> allCache = (List<BookDTO>) bookCache.get(CACHE_GET_ALL).get();
		}
		return StreamSupport.stream(bookDao.findAll().spliterator(), false).map(BookDTO::createByBookPO)
				.collect(toList());
	}

	// 取得單一本書
	@Cacheable(cacheNames = CACHE_NAME_BOOK, key = "#name")
	public BookDTO getBookByName(String name) {
		return bookDao.findByName(name).map(BookDTO::createByBookPO)
				.orElseThrow(() -> DemoException.createByCode(E0001));
	}

	// 新增單一本書
	@CachePut(cacheNames = CACHE_NAME_BOOK, key = "#book.name")
	public BookDTO addBook(BookDTO book) {
		BookPO bookPO = book.getBookPO();
		LocalDate date = DateTimeUtil.stringToDate(book.getPublicationDate());
		bookPO.setPublicationDate(date);
		BookDTO bookDTO = BookDTO.createByBookPO(bookDao.save(bookPO));
		if (bookCache.get(CACHE_GET_ALL) != null) {
			List<BookDTO> allCache = (List<BookDTO>) bookCache.get(CACHE_GET_ALL).get();
			allCache.add(book);
			bookCache.put(CACHE_GET_ALL, allCache);	
		}
		return bookDTO;
	}

	// 刪除單一本書
	@Transactional
	@CacheEvict(cacheNames = CACHE_NAME_BOOK, key = "#name")
	public void removeBook(String name) {
		bookDao.deleteByName(name);
		if(bookCache!=null) {
			List<BookDTO> myCache = MyCacheManager.removeBookCache(name, bookCache);	
			 bookCache.put(CACHE_GET_ALL, myCache);
		}			
	} 

	// 修改一本書
	@Transactional
	@CachePut(cacheNames = CACHE_NAME_BOOK, key = "#book.name")
	public BookDTO editBookByName(BookUpdateDTO book) {

		BookDTO data = updateBook(
				dao -> dao.findByName(book.getName())
						.orElseThrow(() -> DemoException.createByCodeAndExtInfo(E0001, "Can't found Book by name.")),
				bookPO -> {
					if (StringUtils.hasText(book.getName()))
						bookPO.setName(book.getName());
					if (StringUtils.hasText(book.getAuthor()))
						bookPO.setAuthor(book.getAuthor());
					if (StringUtils.hasText(book.getPublicationDate()))
						bookPO.setPublicationDate(DateTimeUtil.stringToDate(book.getPublicationDate()));
				}
		);
		if (bookCache.get(CACHE_GET_ALL) != null) {
			List<BookDTO> allCache = MyCacheManager.editBookCache(book,bookCache);
			bookCache.put(CACHE_GET_ALL, allCache);
		}
		return data;
	}

	public BookDTO updateBook(Function<BookDao, BookPO> selector, Consumer<BookPO> setter) {
		BookPO book = selector.apply(bookDao);
		setter.accept(book);
		return BookDTO.createByBookPO(book);
	}

	// 取得全部書做排序
	@CachePut(cacheNames = CACHE_NAME_BOOK, key = CACHE_GET_ALL_KEY_NAME)
	public List<BookDTO> getAllBookOrderBy(String order) {
		
//		switch (order) {
//
//		case "author":
//			return StreamSupport.stream(bookDao.getAllBookOrderBy(sortByIdAsc(order)).spliterator(), false)
//					.map(BookDTO::createByBookPO).collect(toList());
//
//		case "name":
//			return StreamSupport.stream(bookDao.getAllBookOrderByName().spliterator(), false)
//					.map(BookDTO::createByBookPO).collect(toList());
//		case "publicationDate":
//			return StreamSupport.stream(bookDao.getAllBookOrderBy(sortByIdDesc(order)).spliterator(), false)
//					.map(BookDTO::createByBookPO).collect(toList());
//
//		default:
//			return StreamSupport.stream(bookDao.getAllBookOrderBy(sortByIdAsc(order)).spliterator(), false)
//					.map(BookDTO::createByBookPO).collect(toList());
//
//		}
		if (bookCache.get(CACHE_GET_ALL)!= null) {
			List<BookDTO> allCache = (List<BookDTO>) bookCache.get(CACHE_GET_ALL).get();
			return MyCacheManager.sortBookCache(order, allCache);
		}
		List <BookDTO> BookDTOlist = 
				StreamSupport.stream(bookDao.findAll().spliterator(), false).map(BookDTO::createByBookPO).collect(toList());
		switch(order) {
		case AUTHOR:	
			return BookDTOlist.stream()
			  .sorted(comparing(BookDTO::getAuthor))
			  .collect(toList());
		case NAME:	
			Comparator<BookDTO> compByLength = (aName, bName) -> aName.getName().length() - bName.getName().length();
			return BookDTOlist.stream()
							  .sorted(compByLength.reversed())
							  .collect(toList());
		case PUBLICATIONDATE:			
			return BookDTOlist.stream()
					   		  .sorted(comparing(BookDTO::getPublicationDate).reversed())
					   		  .collect(toList());
		default:
			return BookDTOlist.stream()
							  .collect(toList());
		}
	}
	public Integer getBookCount() {
		return (int) bookDao.count();
	}

//	public Sort sortByIdAsc(String order) {
//		return new Sort(Sort.Direction.ASC, order);
//	}
//
//	public Sort sortByIdDesc(String order) {
//		return new Sort(Sort.Direction.DESC, order);
//	}


		

}
