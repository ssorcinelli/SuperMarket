package it.dstech.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String username;
	
	private String password;
	@Enumerated(EnumType.STRING)
	
	private UserProfileType profileType;
	@JsonIgnore
	@ManyToMany
	@JoinTable(
		      name="USER_PRODJ",
		      joinColumns= @JoinColumn (name="USERJ_ID", referencedColumnName="ID"),
		      inverseJoinColumns=@JoinColumn(name="PRODJ_ID", referencedColumnName="ID")
		      )
	private TipoUtente logtype;
	@JsonIgnore
	@ManyToMany
	@JoinTable(
		      name="USER_PRODJ",
		      joinColumns= @JoinColumn (name="USERJ_ID", referencedColumnName="ID"),
		      inverseJoinColumns=@JoinColumn(name="PRODJ_ID", referencedColumnName="ID")
		      )
	
	private String tel;
	
	private String via;
	
	private String cap;
	
	private String citta;
	
	private String prov;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
	private List<CartaCredito> listaCartaCredito;
	
	public User() {
		
	}

	public User(int id, String username, String password, UserProfileType profileType, TipoUtente logtype, String tel,
			String via, String cap, String citta, String prov, List<CartaCredito> listaCartaCredito) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.profileType = profileType;
		this.logtype = logtype;
		this.tel = tel;
		this.via = via;
		this.cap = cap;
		this.citta = citta;
		this.prov = prov;
		this.listaCartaCredito = listaCartaCredito;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserProfileType getProfileType() {
		return profileType;
	}

	public void setProfileType(UserProfileType profileType) {
		this.profileType = profileType;
	}

	public TipoUtente getLogtype() {
		return logtype;
	}

	public void setLogtype(TipoUtente logtype) {
		this.logtype = logtype;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public List<CartaCredito> getListaCartaCredito() {
		return listaCartaCredito;
	}

	public void setListaCartaCredito(List<CartaCredito> listaCartaCredito) {
		this.listaCartaCredito = listaCartaCredito;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", profileType=" + profileType
				+ ", logtype=" + logtype + ", tel=" + tel + ", via=" + via + ", cap=" + cap + ", citta=" + citta
				+ ", prov=" + prov + ", listaCartaCredito=" + listaCartaCredito + "]";
	}
	
}
	
	