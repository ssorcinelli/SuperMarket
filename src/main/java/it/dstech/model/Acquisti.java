package it.dstech.model;

import javax.persistence.Entity;

@Entity
public class Acquisti {
	
	private int idUser;
	
	private int idFattura;
	
	private int idProdotto;
	
	

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdFattura() {
		return idFattura;
	}

	public void setIdFattura(int idFattura) {
		this.idFattura = idFattura;
	}

	public int getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}
	
	public void setAcquisti(int idUser, int idFattura, int idProdotto) {
		this.idFattura=idFattura;
		this.idUser=idUser;
		this.idProdotto=idProdotto;
	}

}
