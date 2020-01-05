package com.example.demo.dto;



import org.springframework.beans.BeanUtils;

import com.example.demo.bo.BookBO;
import com.example.demo.po.BookPO;
import com.example.demo.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BookUpdateDTO {
	
	private String name;
	private String author;
	private String publicationDate;
	
	public static BookDTO createByUserPO(BookPO po) {
		BookDTO dto = new BookDTO();
		BeanUtils.copyProperties(po, dto);
		
		return dto;
	}
	@JsonIgnore
	public BookBO getBookBO() {
		BookBO bo = new BookBO();
		BeanUtils.copyProperties(this, bo);
		return bo;
	}
	@JsonIgnore
	public BookPO getBookPO(){
		BookPO po = new BookPO();
		this.setPublicationDate(po.getPublicationDate().toString());
		BeanUtils.copyProperties(this, po);
		return po;
	}
	public BookPO getBookPOCache(BookUpdateDTO bookUpdateDTO) {
		BookPO po = new BookPO();
		
		po.setName(bookUpdateDTO.getName());
		po.setAuthor(bookUpdateDTO.getAuthor());
		po.setPublicationDate(DateTimeUtil.stringToDate(bookUpdateDTO.getPublicationDate()));
		
		return  po;
		
	}

	public BookDTO getBookDTO(BookUpdateDTO bookUpdeateDTO) {
		
		BookDTO bookDTO = new BookDTO();
		bookDTO.setName(bookUpdeateDTO.getName());
		bookDTO.setAuthor(bookUpdeateDTO.getAuthor());
		bookDTO.setPublicationDate(bookUpdeateDTO.getPublicationDate());
		
		return bookDTO;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	
	
	
}
