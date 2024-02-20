package com.library.dao;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Integer>{

	public Book getById(int id);
	
	/*
	 * @Query("select * from Book where booktitle like =:?1") public List<Book>
	 * getBooksByName(String booktitle);
	 */
	@Query(value="Select * from Book as b where b.booktitle like %:searchValue%",nativeQuery = true)
	public List<Book> findBybooktitleLike(@Param("searchValue") String searchValue);
	
}
