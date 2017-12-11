package it.dstech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.dstech.model.CartaCredito;

public interface CartaCreditoRepository extends CrudRepository<CartaCredito, Integer>{

	List<CartaCredito> findByUserId(int id);
	
	CartaCredito findById(int id);
}
