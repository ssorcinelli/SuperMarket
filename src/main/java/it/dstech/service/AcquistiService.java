package it.dstech.service;

import java.util.List;

import it.dstech.model.Acquisti;

public interface AcquistiService {
	
	Acquisti saveOrUpdateAcquisti (Acquisti acquisto);
	
	void deleteAcquisti(int id);
	
	List<Acquisti> findAll();
	
	Acquisti findOne(int id);
	
	List<Acquisti> findByIdUser (int id);
	

}
