package it.dstech.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import it.dstech.model.Unita;
import it.dstech.model.User;
import it.dstech.service.AcquistiService;
import it.dstech.service.CartaCreditoService;
import it.dstech.service.ProdottoService;
import it.dstech.service.UserService;

@RestController
@RequestMapping("/prodotto")
public class ProdottoController {

	@Autowired
	private CartaCreditoService creditCardService;

	@Autowired
	private ProdottoService prodottoService;

	@Autowired
	private AcquistiService acqService;

	@Autowired
	private UserService userService;

	private List<Prodotto> lista = new ArrayList<Prodotto>();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/getmodel")
	public ResponseEntity<Prodotto> getmodel() {
		Prodotto prodott = new Prodotto();
		return new ResponseEntity<Prodotto>(prodott, HttpStatus.CREATED);
	}

	@PostMapping("/saveOrUpdate")
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

	@GetMapping("/getlist")
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

	@PostMapping("/acquista/{idCarta}")
	public ResponseEntity<User> acquista(@RequestBody List<Prodotto> prodotti, @PathVariable("idCarta") int idCarta) {
		try {
			CartaCredito card = creditCardService.findById(idCarta);
			logger.info("numero carta: "+card.getNumero());
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
				logger.info("la lista c'è");
				for (Prodotto prodotto : prodotti) {
					if (prodotto.getQuantitaDisponibile() > 0 && dNow.isBefore(scadenza)) {
						logger.info("sto per fare l'acquisto");
						Authentication auth = SecurityContextHolder.getContext().getAuthentication();
						User user = userService.findByUsername(auth.getName());
						LocalDate data = LocalDate.now();
						DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						String dataAcquisto = data.format(dataFormatter).toString();
						logger.info("data acquisto: "+dataAcquisto);
						Acquisti acquisto = new Acquisti(user.getId(), codice, prodotto.getId(), dataAcquisto);
						acqService.saveOrUpdateAcquisti(acquisto);
						logger.info("Transazione: "+acquisto);
						userService.saveUser(user);
						prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile() - 1);
						prodottoService.saveOrUpdateProdotto(prodotto);
					} else {
						logger.info("problema");
					}
				}
			} else {
				logger.error("lista vuota");
			}
			return new ResponseEntity<User>(HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/findById/{prodottoid}")
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

	@GetMapping("/getListDisponibili")
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

	@GetMapping("/findByCategoria/{categoria}")
	public ResponseEntity<List<Prodotto>> findByCategoria(@PathVariable("categoria") Categoria categoria) {
		try {
			List<Prodotto> lista = prodottoService.findByCategoria(categoria);
			return new ResponseEntity<List<Prodotto>>(lista, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<List<Prodotto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getStorico")
	public ResponseEntity<List<Acquisti>> findStorico() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByUsername(auth.getName());
			int idUser = user.getId();
			List<Acquisti> storico = acqService.findByIdUser(idUser);
			return new ResponseEntity<List<Acquisti>>(storico, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<List<Acquisti>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/scadenza")
	private void prodottiScadenza() {
		try {
			List<Prodotto> prodotti = prodottoService.findAll();
			LocalDate oggi = LocalDate.now();
			for (Prodotto p : prodotti) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate dataprodotto = LocalDate.parse(p.getDataDiScadenza(), formatter);
				if (dataprodotto.isBefore(oggi.plusDays(3))) {
					p.setPrezzoUnitario(p.getPrezzoUnitario() - (p.getPrezzoUnitario() * 0.4));
					BigDecimal decimale = new BigDecimal(p.getPrezzoUnitario());
					decimale = decimale.setScale(2, RoundingMode.HALF_UP);
					p.setPrezzoUnitario(decimale.doubleValue());
					prodottoService.saveOrUpdateProdotto(p);
				}
			}
		} catch (Exception e) {
			logger.error("Errore " + e);
		}
	}

	@GetMapping("/getByCategoria/{categoria}/{disponibili}")
	private ResponseEntity<List<Prodotto>> categoriaDisponibile(@PathVariable("categoria")Categoria categoria,@PathVariable("disponibili") double disponibili) {
		try {
			List<Prodotto> lista= new ArrayList<Prodotto>();
			List<Prodotto> listaCategoria = prodottoService.findByCategoria(categoria);
			for (Prodotto prodotto : listaCategoria) {
				if (prodotto.getQuantitaDisponibile() > disponibili) {
					this.lista.add(prodotto);
				}
			}
			return new ResponseEntity<List<Prodotto>>(lista, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<List<Prodotto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/popola")
	public void popola() {
		try {
			Prodotto prodotto = new Prodotto("Mandarini", "Ortofrutta snc", "30/12/2017", Categoria.ALIMENTI, 3.0,
					Unita.CHILO, 1.50, "https://www.casaegiardino.it/images/2013/03/foto-mandarini.jpg", "https://s18.postimg.org/b4u569pmx/banner-mandarini.jpg", 1.0);
			Prodotto prodotto1 = new Prodotto("Riso Scotti Sapori e Emozioni, Basmati Profumato", "Riso Scotti",
					"12/04/2045", Categoria.ALIMENTI, 200, Unita.PEZZO, 1.99,
					"https://images-na.ssl-images-amazon.com/images/I/91D3vbEIpeL._SL1500_.jpg", "https://s18.postimg.org/k14x9nqdl/banner-riso-scotti.jpg", 1.0);
			Prodotto prodotto2 = new Prodotto("Essiccatore Scarpe Deodorante Deumidificatore", "Yosoo", "10/10/3000",
					Categoria.PROD_PERSONA, 34, Unita.PEZZO, 10.99,
					"https://images-na.ssl-images-amazon.com/images/I/61Un2EBawOL._SL1001_.jpg", "https://s18.postimg.org/daog095sp/banner-essicatore-01.jpg", 1.0);
			Prodotto prodotto3 = new Prodotto("Pampers Sole e Luna Salviettine", "Pampers", "10/10/3000",
					Categoria.PROD_PERSONA, 269, Unita.PEZZO, 9.85,
					"https://images-na.ssl-images-amazon.com/images/I/71ZMTLD1jdL._SL1000_.jpg", "https://s18.postimg.org/q0sodv8rd/banner-pampers.jpg", 1.0);
			Prodotto prodotto4 = new Prodotto("Domopak Sacchi Pattumiera Media", "Domo Pak", "10/10/3000",
					Categoria.PROD_CASA, 345, Unita.PEZZO, 1.20,
					"https://images-na.ssl-images-amazon.com/images/I/71hmKalM-gL._SL1500_.jpg", "https://s18.postimg.org/5up6egpt5/banner-_Domopak.jpg", 1.0);
			Prodotto prodotto5 = new Prodotto("Foxy, Carta Igienica mega - Pacco da 4 Rotoli", "Foxy", "10/10/3000",
					Categoria.PROD_CASA, 95, Unita.PEZZO, 2.31,
					"https://images-na.ssl-images-amazon.com/images/I/81t78AN6ptL._SL1500_.jpg", "https://s18.postimg.org/vc7kyjctl/banner--foxy.jpg", 1.0);
			Prodotto prodotto6 = new Prodotto("Whiskas Pranzetti di Casa Pesce", "Whiskas", "12/10/2020",
					Categoria.ANIMALI, 321, Unita.PEZZO, 1.42,
					"https://images-na.ssl-images-amazon.com/images/I/81TK-ktuzRL._SL1500_.jpg", "https://s18.postimg.org/vdhiri489/banner-whiskas.jpg", 1.0);
			Prodotto prodotto7 = new Prodotto("Grey Acchiappacolore", "Grey", "00/00/0000", Categoria.PROD_CASA, 231,
					Unita.PEZZO, 2.94, "https://images-na.ssl-images-amazon.com/images/I/91yUjaXeJKL._SL1500_.jpg", "https://s18.postimg.org/umosm7755/banner-l_acchiappacolori.jpg", 1.0);
			Prodotto prodotto8 = new Prodotto("Durex Settebello Classico Preservativi - 27", "Durex", "20/10/2019",
					Categoria.PROD_PERSONA, 131, Unita.PEZZO, 11.95,
					"https://images-na.ssl-images-amazon.com/images/I/618M-aRQeaL._SL1000_.jpg", "https://s18.postimg.org/yvtioc7tl/banner-_DUREX.jpg", 1.0);
			Prodotto prodotto9 = new Prodotto("Broccoli siciliani", "Broccol srl", "20/12/2017", Categoria.ALIMENTI, 31,
					Unita.CHILO, 6.95, "http://www.mondodelgusto.it/gallery/15994.jpg", "https://s18.postimg.org/6iy0xur89/banner-broccoli.jpg", 1.0);
			Prodotto prodotto10 = new Prodotto("Filetti di Sgombro in Olio di Oliva - 230 gr", "Angelo Parodi",
					"20/12/2018", Categoria.ALIMENTI, 59, Unita.PEZZO, 3.42,
					"https://images-na.ssl-images-amazon.com/images/I/A18E5zUREVL._SL1500_.jpg", "https://s18.postimg.org/sjede1uc9/banner-_FILETTO.jpg", 1.0);
			Prodotto prodotto12 = new Prodotto("Sole Pump Oxy Concentrato", "Sole", "20/12/2019", Categoria.PROD_CASA,
					129, Unita.PEZZO, 1.99,
					"https://images-na.ssl-images-amazon.com/images/I/811z5q-DKJL._SL1500_.jpg", "https://s18.postimg.org/6wzcwzlh5/banner-sole-01.jpg", 1.0);
			Prodotto prodotto13 = new Prodotto("Borotalco Polvere per la Pelle Rinfrescante e Assorbente",
					"Borotalco", "10/02/2019", Categoria.PROD_PERSONA, 109, Unita.PEZZO, 1.23,
					"https://images-na.ssl-images-amazon.com/images/I/81QIZmwegUL._SL1500_.jpg", "https://s18.postimg.org/wehrh1dmh/banner-borotalco.jpg", 1.0);
			Prodotto prodotto14 = new Prodotto("Casaldomo Rocca dei Forti Fragolino", "Casaldomo", "20/01/3000",
					Categoria.ALIMENTI, 10, Unita.PEZZO, 6.97,
					"https://images-na.ssl-images-amazon.com/images/I/71KbgPxX3eL._SL1500_.jpg", "https://s18.postimg.org/hij89hznt/banner-fragolino-01-01.jpg", 1.0);
			Prodotto prodotto15 = new Prodotto("Passata di Pomodoro 100% Italiano", "Mutti", "12/03/2019",
					Categoria.ALIMENTI, 39, Unita.PEZZO, 0.95,
					"https://images-na.ssl-images-amazon.com/images/I/81d7wix3DsL._SL1500_.jpg", "https://s18.postimg.org/wfrp9yfax/banner-pomodoro-01.jpg", 1.0);
			Prodotto prodotto16 = new Prodotto("Pan di Stelle, Biscotti Frollini", "Barilla", "23/03/2018",
					Categoria.ALIMENTI, 233, Unita.PEZZO, 2.77,
					"https://images-na.ssl-images-amazon.com/images/I/91TdlNcj-SL._SL1500_.jpg", "https://s18.postimg.org/xgrxzo46h/banner-pan-di-stelle.jpg", 1.0);
			Prodotto prodotto17 = new Prodotto("Viakal Spray Anticalcare e Discrostante", "Viakal", "02/03/2045",
					Categoria.PROD_CASA, 789, Unita.PEZZO, 1.89,
					"https://images-na.ssl-images-amazon.com/images/I/71NUsRSmrjL._SL1500_.jpg", "https://s18.postimg.org/guadq3qix/banner-viakal.jpg", 1.0);
			Prodotto prodotto18 = new Prodotto("Finish Deodorante per Lavastoviglie Profumo di Limone", "Finish",
					"02/03/2045", Categoria.PROD_CASA, 79, Unita.PEZZO, 2.60,
					"https://images-na.ssl-images-amazon.com/images/I/81d1ql8VI3L._SL1500_.jpg", "https://s18.postimg.org/ub7c8y5zd/banner-finish.jpg", 1.0);
			Prodotto prodotto19 = new Prodotto("Schizzetto 4 CC 65", "Sunstar", "02/03/2045", Categoria.PROD_CASA, 21,
					Unita.PEZZO, 6.49, "https://images-na.ssl-images-amazon.com/images/I/61AS2U09nzL._SL1000_.jpg", "https://s18.postimg.org/kqnpm3j89/banner-schizzo-01.jpg", 1.0);
			Prodotto prodotto20 = new Prodotto("Gourmet Gold Tortini Pesce - 24 Pezzi da 4x85 gr", "Gourmet",
					"12/08/2020", Categoria.ANIMALI, 29, Unita.PEZZO, 59.28,
					"https://images-na.ssl-images-amazon.com/images/I/81or%2B9FftbL._SL1500_.jpg", "https://s18.postimg.org/kdwbftli1/banner-gourmet.jpg", 1.0);

			prodottoService.saveOrUpdateProdotto(prodotto);
			prodottoService.saveOrUpdateProdotto(prodotto1);
			prodottoService.saveOrUpdateProdotto(prodotto2);
			prodottoService.saveOrUpdateProdotto(prodotto3);
			prodottoService.saveOrUpdateProdotto(prodotto4);
			prodottoService.saveOrUpdateProdotto(prodotto5);
			prodottoService.saveOrUpdateProdotto(prodotto6);
			prodottoService.saveOrUpdateProdotto(prodotto7);
			prodottoService.saveOrUpdateProdotto(prodotto8);
			prodottoService.saveOrUpdateProdotto(prodotto9);
			prodottoService.saveOrUpdateProdotto(prodotto10);
			prodottoService.saveOrUpdateProdotto(prodotto12);
			prodottoService.saveOrUpdateProdotto(prodotto13);
			prodottoService.saveOrUpdateProdotto(prodotto14);
			prodottoService.saveOrUpdateProdotto(prodotto15);
			prodottoService.saveOrUpdateProdotto(prodotto16);
			prodottoService.saveOrUpdateProdotto(prodotto17);
			prodottoService.saveOrUpdateProdotto(prodotto18);
			prodottoService.saveOrUpdateProdotto(prodotto19);
			prodottoService.saveOrUpdateProdotto(prodotto20);
			new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Errore: " + e);
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
