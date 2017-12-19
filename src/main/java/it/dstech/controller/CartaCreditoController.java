package it.dstech.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dstech.model.CartaCredito;
import it.dstech.model.User;
import it.dstech.service.CartaCreditoService;
import it.dstech.service.UserService;

@RestController
@RequestMapping("/cartaCredito")
public class CartaCreditoController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CartaCreditoService cartaCreditoService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getmodel")
	public ResponseEntity<CartaCredito> getmodel() {
		CartaCredito cartaCredito = new CartaCredito();
		return new ResponseEntity<CartaCredito>(cartaCredito, HttpStatus.OK);
	}
	
	@PostMapping("/saveupdate")
	public ResponseEntity<CartaCredito> saveOrUpdateCard(@RequestBody CartaCredito cartaCredito) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByUsername(auth.getName());	
			cartaCredito.setUser(user);
			String numeroCarta = cartaCredito.getNumero();
			String encoded = new String(Base64.getEncoder().encode(numeroCarta.getBytes()));
			cartaCredito.setNumero(encoded);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
		    String date = cartaCredito.getScadenza();
		    YearMonth scadenzaMese = YearMonth.parse(date, formatter);
		    LocalDate scadenza = scadenzaMese.atEndOfMonth();
		    logger.info("Data di scadenza"+scadenza);
			if (!scadenza.isAfter(LocalDate.now())) {
				logger.error("La data di scadenza non è valida");
				return new ResponseEntity<CartaCredito>(HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				CartaCredito saved = cartaCreditoService.saveCartaCredito(cartaCredito);
			return new ResponseEntity<CartaCredito>(saved, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<CartaCredito>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getall")
	public ResponseEntity<List<CartaCredito>> getAll() {
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			logger.info("aaaa"+auth.getName());
//			Principal principal = request.getUserPrincipal();
//			logger.info("aaaa"+principal.getName());
			User user = userService.findByUsername(auth.getName());	
			logger.info(user.toString());
			List<CartaCredito> listaCard = cartaCreditoService.findByUserId(user.getId());
			return new ResponseEntity<List<CartaCredito>>(listaCard, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("a"+e);
			return new ResponseEntity<List<CartaCredito>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/deletecard/{cardid}")
	public ResponseEntity<User> deleteCard(@PathVariable("cardid") int idCard) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByUsername(auth.getName());	
			CartaCredito cartaCredito = cartaCreditoService.findById(idCard);
			List<CartaCredito> listaCard = cartaCreditoService.findByUserId(user.getId());
			listaCard.remove(cartaCredito);
			user.setListaCartaCredito(listaCard);	
			userService.saveUser(user);
			return new ResponseEntity<User>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
