package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.po.BookPO;

public interface BookDao extends CrudRepository<BookPO, Integer> {
	
	Optional<BookPO> findByName(String name);
	
	void deleteByName(String name);
	
	List <BookPO> getAllBookOrderBy(Sort sort);	
	//做排序
	@Query(value="SELECT * FROM DEMO_BOOK ORDER BY LENGTH(NAME) DESC ",nativeQuery=true)
	List <BookPO> getAllBookOrderByName();
}
