package jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe JPA LanguageToTalk
 */
@Entity
@Table(name="practicalcase.location")
public class LocationJPA implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "num")
	private String num;
	
	@Column(name = "cp")
	private String cp;
	
	@Column(name = "city")
	private String city;
	
	public LocationJPA(){
		super();
	}
	
	public LocationJPA(String street, String num, String cp, String city){
		this.street = street;
		this. num = num;
		this.cp = cp;
		this.city = city;
	}

	/**
	 * Getters i setters
	 */
	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String toString(){
		return getStreet() + ", " + getNum() + " " + getCp() + " " + getCity();
	}
}
