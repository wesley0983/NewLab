package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserBookDTO;
import com.example.demo.service.UserBookService;



@RestController
@RequestMapping(path = "/adduserbook")
public class UserBookController {
	
	@Autowired
	private UserBookService userBookService;
	
	@PostMapping
	public UserBookDTO addUser(@RequestBody UserBookDTO userDTO) {
		return userBookService.addUserBook(userDTO);
	}
	@PutMapping
	public UserBookDTO editUserByUserSeqNBookByName(@RequestBody UserBookDTO bookDTO) {
		return userBookService.editUserByUserSeqNBookByName(bookDTO);	
	}
}
