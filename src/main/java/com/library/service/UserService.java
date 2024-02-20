package com.library.service;

import com.library.model.User;

import java.util.List;



public interface UserService  {

	public void save(User user);
	
	public List<User> getAllUsers();
	//public Optional<User> getUserById(int id);
	public void deleteuser(int id);
	
}
