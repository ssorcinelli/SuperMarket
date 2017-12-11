package it.dstech.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.dstech.model.User;
import it.dstech.repository.UserRepository;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repo1;

	@Override
	public User saveUser(User user) {
		return repo1.save(user);
	}

	@Override
	public void deleteUser(int id) {
		repo1.delete(id);
	}
	
	@Override
	public User findByUsername(String username) {
		return repo1.findByUsername(username);
	}	

}
