package com.library.service;

import com.library.dao.SellBookDao;
import com.library.model.SellBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellBookServiceImpl implements SellBookService{

	@Autowired
	SellBookDao sellBookDao;
	
	@Override
	public void sellBookSave(SellBook sbook) {
		this.sellBookDao.saveAndFlush(sbook);
	}

	@Override
	public List<SellBook> getAllSellBook() {
		return this.sellBookDao.findAll();
	}

}
