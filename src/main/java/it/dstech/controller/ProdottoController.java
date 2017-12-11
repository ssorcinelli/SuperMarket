package it.dstech.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dstech.model.Acquisti;
import it.dstech.model.CartaCredito;
import it.dstech.model.Categoria;
import it.dstech.model.Prodotto;
import it.dstech.model.User;
import it.dstech.service.CartaCreditoService;
import it.dstech.service.ProdottoService;
import it.dstech.service.UserService;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {

	@Autowired
	private CartaCreditoService creditCardService;

	@Autowired
	private ProdottoService prodottoService;

	@Autowired
	private UserService userService;

	@Autowired
	private Acquisti acquisto;
	
	private List<Prodotto> lista;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/getmodel")
	public ResponseEntity<Prodotto> getmodel() {
		Prodotto prodott = new Prodotto();
		return new ResponseEntity<Prodotto>(prodott, HttpStatus.CREATED);
	}

	@PostMapping("/prodotto/saveOrUpdate")
	public ResponseEntity<Prodotto> saveOrUpdateProdotto(@RequestBody Prodotto prodotto) {
		try {
			Prodotto saved = prodottoService.saveOrUpdateProdotto(prodotto);
			logger.info(saved + "saved");
			return new ResponseEntity<Prodotto>(saved, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Prodotto> deleteProdotto(@PathVariable("id") int id) {
		try {
			prodottoService.deleteProdotto(id);
			logger.info(id + " deleted");
			return new ResponseEntity<Prodotto>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/prodotto/getlist")
	public ResponseEntity<List<Prodotto>> getAll() {
		try {
			List<Prodotto> listaProdotti = prodottoService.findAll();
			logger.info(listaProdotti.toString());
			return new ResponseEntity<List<Prodotto>>(listaProdotti, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<List<Prodotto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/prodotto/acquista/{carta}")
	public ResponseEntity<User> acquista(@RequestBody List<Prodotto> prodotti, @PathVariable("carta") int idCarta) {
		try {
			CartaCredito card = creditCardService.findById(idCarta);
			LocalDate dNow = LocalDate.now();
			logger.info("anno" + dNow);
			// -----
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
			String date = card.getScadenza().toString();
			YearMonth scadenzaMese = YearMonth.parse(date, formatter);
			LocalDate scadenza = scadenzaMese.atEndOfMonth();
			// -----
			logger.info("anno" + scadenza);
			logger.info("prova" + dNow.isBefore(scadenza));
			Random random = new Random();
			int codice = random.nextInt(999);
			if (!prodotti.isEmpty()) {
				for (Prodotto prodotto : prodotti) {

					if (prodotto.getQuantitaDisponibile() > 0 && dNow.isBefore(scadenza)) {
						Authentication auth = SecurityContextHolder.getContext().getAuthentication();
						User user = userService.findByUsername(auth.getName());
						this.acquisto.setAcquisti(user.getId(), codice, prodotto.getId());
						userService.saveUser(user);
						prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile() - 1);
						prodottoService.saveOrUpdateProdotto(prodotto);

						return new ResponseEntity<User>(HttpStatus.OK);
					} else {
						return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
			} else
				logger.error("lista vuota");
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/prodotto/{prodottoid}")
	public ResponseEntity<Prodotto> findProdottoById(@PathVariable("prodottoid") int id) {
		try {
			Prodotto prodotto = prodottoService.findById(id);
			logger.info(id + " findById");
			return new ResponseEntity<Prodotto>(prodotto, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/prodotti/getListDisponibili")
	public ResponseEntity<List<Prodotto>> getListDisponibili() {
		try {
			List<Prodotto> listaProdotti = prodottoService.findAll();
			for (Prodotto prodotto : listaProdotti) {
				if (prodotto.getQuantitaDisponibile() > 0) {
					this.lista.add(prodotto);
				}
			}
			return new ResponseEntity<List<Prodotto>>(this.lista, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<List<Prodotto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/prodotti/getCategoria/{categoria}") 
	public ResponseEntity <List<Prodotto>> getCategoria(@PathVariable("categoria") Categoria categoria){
		try {
			List<Prodotto> lista= prodottoService.findByCategoria(categoria);
			return new ResponseEntity<List<Prodotto>> (lista, HttpStatus.OK);
		}catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<List<Prodotto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
