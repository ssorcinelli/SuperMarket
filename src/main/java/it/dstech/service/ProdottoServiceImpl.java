package it.dstech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.model.Prodotto;
import it.dstech.repository.ProdottoRepository;


@Service
public class ProdottoServiceImpl implements ProdottoService {

	@Autowired
	ProdottoRepository prodottoRepository;
	
	@Override
	public Prodotto saveOrUpdateProdotto(Prodotto prodotto) {
		return prodottoRepository.save(prodotto);
	}

	@Override
	public List<Prodotto> findAll() {
		return (List<Prodotto>) prodottoRepository.findAll();
	}

	@Override
	public void deleteProdotto(int id) {
		prodottoRepository.delete(id);
	}
	@Override
	public Prodotto findById(int id) {
		return prodottoRepository.findOne(id);
	}
	

}
