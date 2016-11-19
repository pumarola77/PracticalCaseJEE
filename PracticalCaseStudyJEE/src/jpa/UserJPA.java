package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * Classe JPA UserJPA
 */
@Entity
@Table(name="practicalcase.user")
public class UserJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nif;
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String email;
	
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
}
