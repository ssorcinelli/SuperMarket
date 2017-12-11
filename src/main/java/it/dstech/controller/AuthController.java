package it.dstech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.dstech.model.User;
import it.dstech.service.UserService;
import it.dstech.service.auth.AuthService;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@PostMapping("/login")
	public UserDetails authenticate(@RequestBody User principal) throws Exception {
		return authService.authenticate(principal);
	}

	@PostMapping("/register")
	public User addUser(@RequestBody User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userService.saveUser(user);
	}

	@GetMapping("/getUserModel")
	public User getModel() {
		return new User();
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable int id) {
		userService.deleteUser(id);
	}
}
