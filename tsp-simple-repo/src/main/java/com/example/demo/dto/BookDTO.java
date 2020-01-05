package com.example.demo.dto;




import org.springframework.beans.BeanUtils;

import com.example.demo.bo.BookBO;
import com.example.demo.po.BookPO;
import com.example.demo.util.DateTimeUtil;
import com.example.demo.validator.annotation.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import static com.example.demo.constant.VerifyRegexConst.PATTERN_NAME;

import javax.validation.constraints.NotNull;

import static com.example.demo.constant.VerifyRegexConst.PATTERN_AUTHOR;
import static com.example.demo.constant.ValidatorEnum.VALIDATE_PUBLICDATE;

public class BookDTO {
	@Validator(notNull = true,pattern = PATTERN_NAME)
	private String name;
	@Validator(notNull = true,pattern = PATTERN_AUTHOR)
	private String author;
    @Validator(notNull = true,validator = VALIDATE_PUBLICDATE)
	private String publicationDate;

	// 增進效能選擇要用的屬性
	public static BookDTO createByBookPO(BookPO po) {
		BookDTO dto = new BookDTO();
		BeanUtils.copyProperties(po, dto);
		
		dto.setPublicationDate(po.getPublicationDate().toString());
		
		return dto;
	}

	@JsonIgnore
	public BookBO getBookBO() {
		BookBO bo = new BookBO();
		BeanUtils.copyProperties(this, bo);

		return bo;
	}

	@JsonIgnore
	public BookPO getBookPO() {
		BookPO po = new BookPO();
		po.setPublicationDate(DateTimeUtil.stringToDate(this.getPublicationDate()));
		BeanUtils.copyProperties(this, po);
		return po;
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
