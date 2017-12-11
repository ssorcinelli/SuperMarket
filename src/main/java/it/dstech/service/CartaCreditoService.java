package it.dstech.service;

import java.util.List;

import it.dstech.model.CartaCredito;

public interface CartaCreditoService {
	
	CartaCredito saveCartaCredito(CartaCredito cartaCredito);
	
	void deleteCartaCredito(int id);
	
	List<CartaCredito> findByUserId(int id);
	
	public CartaCredito findById(int id);

}
