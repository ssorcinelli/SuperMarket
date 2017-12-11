package it.dstech.service;

import java.util.List;

import it.dstech.model.Categoria;
import it.dstech.model.Prodotto;


public interface ProdottoService {

	Prodotto saveOrUpdateProdotto(Prodotto prodotto);
	
	List<Prodotto> findAll();
	
	void deleteProdotto(int id);
	
	Prodotto findById(int id);
	
	List<Prodotto> findByCategoria(Categoria categoria);
	
}
