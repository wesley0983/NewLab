package com.example.demo.po;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "DEMO_BOOK")
@DynamicInsert
@DynamicUpdate
public class BookPO {

	@Id
	@Column(name = "BOOK_SEQ", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookSeq;

	@Column(name = "NAME", unique = true, length = 30)
	private String name;

	@Column(name = "AUTHOR", unique = false, length = 30)
	private String author;
	
	@Column(name = "PUBLICATION_DATE",unique = false, length = 100)
	private LocalDate publicationDate;

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

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}

	
}
