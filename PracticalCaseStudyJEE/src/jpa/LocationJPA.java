package jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Identifica la localitzacio
 * 
 * @author Grup 6
 * @since 1.0
 */
@Entity
@Table(name="practicalcase.location")
public class LocationJPA implements Serializable {
	
	/**
	 * Obligatori perque la classe implementa serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identificador location
	 */
	@Id
	@Column(name = "idlocation", nullable=false, columnDefinition="serial")	
	private Long id;
	
	/**
	 * Identifica el carrer
	 */
	@Column(name = "street")
	private String street;
	
	/**
	 * Identifica el num
	 */
	@Column(name = "num")
	private String num;
	
	/**
	 * Identifica el codi postal
	 */
	@Column(name = "cp")
	private String cp;
	
	/**
	 * Identifica la ciutat
	 */
	@Column(name = "city")
	private String city;
	
	/**
	 * Constructor
	 */
	public LocationJPA(){
		super();
	}
	
	/**
	 * Constructor amb parametres
	 * 
	 * @param street Identifica el carrer
	 * @param num Identifica el numero
	 * @param cp Identifica el codi postal
	 * @param city Identifica la ciutat
	 */
	public LocationJPA(String street, String num, String cp, String city){
		this.street = street;
		this. num = num;
		this.cp = cp;
		this.city = city;
	}

	/**
	 * Obte identificador
	 * 
	 * @return identificador de la ciutat
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Assigna identificador
	 * 
	 * @param id identificador de la classe
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obte carrer
	 * 
	 * @return retorna el carrer de la localitzacio
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Assigna carrera de la localitzacio
	 * 
	 * @param street Assigna el carrer
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Obte el número de la localització
	 * @return número de la localització
	 */
	public String getNum() {
		return num;
	}

	/**
	 * Retorna el número del carrer
	 * @param num número del carrer
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * Obte codi postal
	 * @return codi postal
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * Assigna codi postal
	 * @param cp codi postal
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * Obte ciutat 
	 * @return ciutat
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Assigna ciutat
	 * @param city nom de la ciutat
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Metode toString de la classe
	 */
	public String toString(){
		return getStreet() + ", " + getNum() + " " + getCp() + " " + getCity();
	}
}
