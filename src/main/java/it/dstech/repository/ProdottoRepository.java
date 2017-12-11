package it.dstech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import it.dstech.model.Prodotto;


public interface ProdottoRepository extends CrudRepository<Prodotto, Integer> {

	Prodotto findById(int id);
	
//	List<Prodotto> findByUser_id (int id);
	
}
