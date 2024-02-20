package com.library.service;

import com.library.dao.UserDao;
import com.library.model.User;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userdao;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional
	public void save(User user) {
		this.userdao.save(user);

	}

	
	
	
	@Override
	@Transactional
	public List<User> getAllUsers() {

		return this.userdao.findAll();

	}

	/*
	 * @Override
	 * 
	 * @Transactional public Optional<User> getUserById(int id) {
	 * 
	 * return this.userdao.findById(id);
	 * 
	 * }
	 */
	@Override
	@Transactional
	public void deleteuser(int id) {

		this.userdao.deleteById(id);

	}




	

}
