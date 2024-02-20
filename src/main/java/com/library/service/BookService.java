package com.library.service;

import com.library.model.Book;

import java.util.List;



public interface BookService {

	public void save(Book book);
	public List<Book> getBooks();
	//public Optional<Book> getBookById(int id);
	public void deleteBook(int id);
	
	
	
}
