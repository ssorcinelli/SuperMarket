package it.dstech.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CartaCredito {
	
	private int id;
	
	private String numero;
	
	private String scadenza;
	
	private String ccv;
	
	private double credito;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	public CartaCredito() {
		
	}

	public CartaCredito(int id, String numero, String scadenza, String ccv, double credito, User user) {
		this.id = id;
		this.numero = numero;
		this.scadenza = scadenza;
		this.ccv = ccv;
		this.credito = credito;
		this.user = user;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public String getScadenza() {
		return scadenza;
	}



	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}



	public String getCcv() {
		return ccv;
	}



	public void setCcv(String ccv) {
		this.ccv = ccv;
	}



	public double getCredito() {
		return credito;
	}



	public void setCredito(double credito) {
		this.credito = credito;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	@Override
	public String toString() {
		return "CartaCredito [id=" + id + ", numero=" + numero + ", scadenza=" + scadenza + ", ccv=" + ccv
				+ ", credito=" + credito + ", user=" + user + "]";
	}
	
	
	

}
