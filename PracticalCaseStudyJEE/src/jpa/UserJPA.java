package jpa;
import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

/**
 * Classe JPA UserJPA
 */
@Entity
@Table(name="practicalcase.user",  uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class UserJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="nif")
	private String nif;
	
	@Column(name="name")
	private String name;
	
	@Column(name="surname")
	private String surname;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
	private Collection<TalkedLanguageJPA> talkedLanguages;
	
	/**
	 * Constructors
	 */
	public UserJPA() {
		super();
	}
	
	public UserJPA(String nif, String name, String surname, String phone, String password, String email) {		
		this.nif = nif;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.password = password;
		this.email = email;
	}

	/**
	 *  Mètodes get/set dels camps de la base de dades
	 */
	@Id
	public String getNif() {
		return nif;
	}
	public  void setNif(String nif) {
		this.nif = nif;
	}
	public String getName() {
		return name;
	}	
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public  void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhone() {
		return phone;
	}
	public  void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public  void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public  void setEmail(String email) {
		this.email = email;
	}
	
	/* 
	 * Metodes get/set persistence relationships 
	 * */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn(name = "nif")
	public Collection<TalkedLanguageJPA> getTalkedLanguageByUser() {
		return talkedLanguages;
	}
	
	public void setTalkedLanguageByUser(Collection<TalkedLanguageJPA> talkedlanguages) {
		this.talkedLanguages = talkedlanguages;
	}
	
	
}
