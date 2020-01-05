package com.example.demo.dto;

import static com.example.demo.constant.VerifyRegexConst.PATTERN_AUTHOR;
import static com.example.demo.constant.VerifyRegexConst.PATTERN_NAME;
import static com.example.demo.constant.VerifyRegexConst.PATTERN_PUBLICATIONDATE;

import org.springframework.beans.BeanUtils;


import com.example.demo.po.BookPO;
import com.example.demo.po.UserPO;
import com.example.demo.validator.annotation.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Data Transfer Object
 *
 * 數據傳輸物件主要用於遠程調用等需要大量傳輸物件的地方。 比如我們一張表有100個欄位，那麼對應的 PO 就有100個屬性。
 * 但是我們畫面上只要顯示其中的10個欄位，客戶端用 WEB service 來獲取數據，沒有必要把整個 PO 物件傳遞到客戶端，
 * 這時我們就可以用只有這10個屬性的 DTO 來傳遞結果到客戶端，這樣也不會暴露服務端表結構。
 */

public class UserBookDTO {

	@Validator(notNull = true,pattern = PATTERN_NAME)
	private String name;
	@Validator(notNull = true,pattern = PATTERN_AUTHOR)
	private String author;
	@Validator(notNull = true,pattern = PATTERN_PUBLICATIONDATE)
	private String publicationDate;

	private int userSeq;
	private String userCode;
	private String pcode;
	private String role;

	public static UserBookDTO createByUserPOBookPO(UserPO userpo, BookPO bookpo) {

		UserBookDTO dto = new UserBookDTO();
		BeanUtils.copyProperties(userpo, dto);
		BeanUtils.copyProperties(bookpo, dto);
		dto.setPublicationDate(bookpo.getPublicationDate().toString());
		
		return dto;
	}

	@JsonIgnore
	public UserPO getUserPO() {
		UserPO po = new UserPO();
		BeanUtils.copyProperties(this, po);

		return po;
	}

	@JsonIgnore
	public BookPO getBookPO() {
		BookPO po = new BookPO();
		BeanUtils.copyProperties(this, po);
		//第二種方法
//		po.setPublicationDate(DateTimeUtil.stringToDate(this.getPublicationDate()));

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
	public int getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
