package it.dstech.model;

import java.time.LocalDate;

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
	
	private LocalDate data;
	
	

	public Acquisti(int id, int idUser, int idFattura, int idProdotto, LocalDate data) {
		this.id = id;
		this.idUser = idUser;
		this.idFattura = idFattura;
		this.idProdotto = idProdotto;
		this.data = data;
	}
	
	public Acquisti(int idUser, int idFattura, int idProdotto, LocalDate data) {
		this.idUser = idUser;
		this.idFattura = idFattura;
		this.idProdotto = idProdotto;
		this.data = data;
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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Acquisti [id=" + id + ", idUser=" + idUser + ", idFattura=" + idFattura + ", idProdotto=" + idProdotto
				+ ", data=" + data + "]";
	}
}
