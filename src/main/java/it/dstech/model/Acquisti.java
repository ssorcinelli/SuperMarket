package it.dstech.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Acquisti {
	
	@Id
	@GeneratedValue
	private int id;
	
	private int idUser;
	
	private int idFattura;
	
	private int idProdotto;
	
	

	public Acquisti(int id, int idUser, int idFattura, int idProdotto) {
		this.id = id;
		this.idUser = idUser;
		this.idFattura = idFattura;
		this.idProdotto = idProdotto;
	}
	
	public Acquisti(int idUser, int idFattura, int idProdotto) {
		this.idUser = idUser;
		this.idFattura = idFattura;
		this.idProdotto = idProdotto;
	}
	
	public Acquisti() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "Acquisti [id=" + id + ", idUser=" + idUser + ", idFattura=" + idFattura + ", idProdotto=" + idProdotto
				+ "]";
	}
}
