package com.example.demo.bo;

import java.sql.Date;

/**
 * select
 */
public class BookBO {

	private String name;
	private String author;
	private Date publicationDate;

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

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@Override
	public String toString() {
		return "BookBO{" + "name='" + name + '\'' + ", author='" + author + '\'' + ", publicationDate='"
				+ publicationDate + '\'' + '}';
	}

}
