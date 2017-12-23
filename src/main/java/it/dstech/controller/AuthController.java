package it.dstech.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
	
	@GetMapping("/findByUsername")
	public ResponseEntity<User> findByUsername(@RequestHeader("username") String username) {
		try {
			User found = userService.findByUsername(username);
			logger.info(found.getPassSocial());
			return new ResponseEntity<User>(found, HttpStatus.OK);
		} catch (Exception e) {
		logger.error("Error: "+e);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findPassSocial")
	public ResponseEntity<String> findPassSocialByUsername(@RequestHeader("username") String username) {
		try {
			User found = userService.findByUsername(username);
			logger.info(found.getPassSocial());
			return new ResponseEntity<String>(found.getPassSocial(), HttpStatus.OK);
		} catch (Exception e){
			logger.error("Error: "+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@CrossOrigin
	@RequestMapping(value= "/logoutApp", method = RequestMethod.OPTIONS)
	public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}
}
