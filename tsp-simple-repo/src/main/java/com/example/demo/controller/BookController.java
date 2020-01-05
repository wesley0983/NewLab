package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.BookUpdateDTO;
import com.example.demo.service.BookService;

@RestController
@RequestMapping(path = "/book")
public class BookController {

	@Autowired
	private BookService bookService;

//	@GetMapping
//	public List<BookDTO> getAllBook(){
//		return bookService.getAllBook();
//	}
	@GetMapping(path = "/{name}")
	public BookDTO getBook(@PathVariable("name") String name) {
		System.out.println(name);
		return bookService.getBookByName(name);
	}

	@PostMapping
	public BookDTO addBook(@RequestBody BookDTO book) {
		return bookService.addBook(book);
	}

	@DeleteMapping(path = "/{name}")
	public void removeBook(@PathVariable("name") String name) {
		bookService.removeBook(name);
	}

	@PutMapping
	public BookDTO editUser(@RequestBody BookUpdateDTO book) {
		return bookService.editBookByName(book);
	}

	@GetMapping
	public List<BookDTO> getAllBookOrderBy(@RequestParam(value = "order", required = false) String order) {
		Optional<String> equalnull = Optional.ofNullable(order);
//		if (equalnull.isPresent()) {
//			return bookService.getAllBookOrderBy(order);
//		}
//		return bookService.getAllBook();
		return equalnull.map(e->bookService.getAllBookOrderBy(e))
						.orElse(bookService.getAllBook());
						
	
	}
	
	
}
