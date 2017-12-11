package it.dstech.service;


import it.dstech.model.User;

public interface UserService {
	
	User saveUser(User user);
	
	void deleteUser(int id);
	
	User findByUsername(String username);
	
}
