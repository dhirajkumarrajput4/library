package com.library.dao;

import com.library.model.SellBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellBookDao extends JpaRepository<SellBook, Integer>{

}
