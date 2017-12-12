package it.dstech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.dstech.model.Acquisti;

public interface AcquistiRepository extends CrudRepository<Acquisti, Integer> {
	
	List<Acquisti> findByIdUser (int id);

}
