package it.dstech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.model.CartaCredito;
import it.dstech.repository.CartaCreditoRepository;
@Service
public class CartaCreditoServiceImpl implements CartaCreditoService{
	
	@Autowired
	private CartaCreditoRepository repo;
	
	@Override
	public CartaCredito saveCartaCredito(CartaCredito cartaCredito) {
		return repo.save(cartaCredito);
	}

	@Override
	public void deleteCartaCredito(int id) {
		repo.delete(id);
	}

	@Override
	public List<CartaCredito> findByUserId(int id) {
		return repo.findByUserId(id);
	}

	@Override
	public CartaCredito findById(int id) {
		return repo.findById(id);
	}

}
