package com.library.service;

import com.library.model.SellBook;

import java.util.List;

public interface SellBookService {

	public void sellBookSave(SellBook sbook);
	public List<SellBook> getAllSellBook();
	
}
