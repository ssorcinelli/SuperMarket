package it.dstech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.model.Acquisti;
import it.dstech.repository.AcquistiRepository;

@Service
public class AcquistiServiceImpl implements AcquistiService{
	
	@Autowired
	private AcquistiRepository repo;

	@Override
	public Acquisti saveOrUpdateAcquisti(Acquisti acquisto) {
		return repo.save(acquisto);
	}

	@Override
	public void deleteAcquisti(int id) {
		repo.delete(id);
	}

	@Override
	public List<Acquisti> findAll() {
		return (List<Acquisti>) repo.findAll();
	}

	@Override
	public Acquisti findOne(int id) {
		return repo.findOne(id);
	}

}
