package it.dstech.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Prodotto {
	@Id
	@GeneratedValue
	private int Id;
	
	private String nome;
	
	private String marca;
	
	private LocalDate dataDiScadenza;
	
	private Categoria categoria;
	
	private double quantitaDisponibile;
	
	private double quantitaDaAcquistare;
	
	private Unita unita;
	
	private double prezzoUnitario;
	
	private double prezzoSenzaIva;
	
	private double prezzoIvato;
	
	private String img;
	
	private int offerta;
	
	public Prodotto(int id, String nome, String marca, LocalDate dataDiScadenza, Categoria categoria,
			double quantitaDisponibile, double quantitaDaAcquistare, Unita unita, double prezzoUnitario,
			double prezzoSenzaIva, double prezzoIvato, String img, int offerta) {
		Id = id;
		this.nome = nome;
		this.marca = marca;
		this.dataDiScadenza = dataDiScadenza;
		this.categoria = categoria;
		this.quantitaDisponibile = quantitaDisponibile;
		this.quantitaDaAcquistare = quantitaDaAcquistare;
		this.unita = unita;
		this.prezzoUnitario = prezzoUnitario;
		this.prezzoSenzaIva = prezzoSenzaIva;
		this.prezzoIvato = prezzoIvato;
		this.img = img;
		this.offerta = offerta;
	}
	
	public Prodotto() {
		
	}

	public Prodotto(String nome, String marca, LocalDate dataDiScadenza, Categoria categoria,
			double quantitaDisponibile, Unita unita, double prezzoUnitario, String img) {
		this.nome = nome;
		this.marca = marca;
		this.dataDiScadenza = dataDiScadenza;
		this.categoria = categoria;
		this.quantitaDisponibile = quantitaDisponibile;
		this.unita = unita;
		this.prezzoUnitario = prezzoUnitario;
		this.img = img;
	}



	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public LocalDate getDataDiScadenza() {
		return dataDiScadenza;
	}

	public void setDataDiScadenza(LocalDate dataDiScadenza) {
		this.dataDiScadenza = dataDiScadenza;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public double getQuantitaDisponibile() {
		return quantitaDisponibile;
	}

	public void setQuantitaDisponibile(double quantitaDisponibile) {
		this.quantitaDisponibile = quantitaDisponibile;
	}

	public double getQuantitaDaAcquistare() {
		return quantitaDaAcquistare;
	}

	public void setQuantitaDaAcquistare(double quantitaDaAcquistare) {
		this.quantitaDaAcquistare = quantitaDaAcquistare;
	}

	public Unita getUnita() {
		return unita;
	}

	public void setUnita(Unita unita) {
		this.unita = unita;
	}

	public double getPrezzoUnitario() {
		return prezzoUnitario;
	}

	public void setPrezzoUnitario(double prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}

	public double getPrezzoSenzaIva() {
		return prezzoSenzaIva;
	}

	public void setPrezzoSenzaIva(double prezzoSenzaIva) {
		this.prezzoSenzaIva = this.prezzoIvato - (this.prezzoIvato/100*22);
	}

	public double getPrezzoIvato() {
		return prezzoIvato;
	}

	public void setPrezzoIvato(double prezzoIvato) {
		this.prezzoIvato = prezzoIvato;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getOfferta() {
		return offerta;
	}

	public void setOfferta(int offerta) {
		this.offerta = offerta;
	}

	@Override
	public String toString() {
		return "Prodotto [Id=" + Id + ", nome=" + nome + ", marca=" + marca + ", dataDiScadenza=" + dataDiScadenza
				+ ", categoria=" + categoria + ", quantitaDisponibile=" + quantitaDisponibile
				+ ", quantitaDaAcquistare=" + quantitaDaAcquistare + ", unita=" + unita + ", prezzoUnitario="
				+ prezzoUnitario + ", prezzoSenzaIva=" + prezzoSenzaIva + ", prezzoIvato=" + prezzoIvato + "]";
	}
	
	

}
