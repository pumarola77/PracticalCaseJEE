package jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Classe relacionada amb les llengues que vol parlar usuari
 * 
 * @author Grup 6
 * @version 1.0
 */
@Entity
//@Table(name="practicalcase.languagetotalk",  uniqueConstraints={@UniqueConstraint(columnNames = {"nif", "language"})})
@Table(name="practicalcase.languagetotalk")
public class LanguageToTalkJPA implements Serializable {
	
	/**
	 * Obligatori perque la classe implementa serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Identificador
	 */
	@Id
	@Column(name = "language")
	private String language;

	/**
	 * 
	 * Nivell del llenguatge
	 */
	@Column(name = "level")
	private String level;
	
	/**
	 * 
	 * Descripcio perque vol parlar el llenguatge
	 */
	@Column(name = "description")
	private String description;
	
	/**
	 * 
	 * Acceptaria pagar per practicar el llenguatge
	 */
	@Column(name = "acceptPay")
	private boolean acceptPay;
	
	/**
	 * 
	 * Identificador usuari
	 */
	@Id
	@ManyToOne
	@JoinColumn (name="nif")
	private UserJPA user;

	/**
	 * Constructor de la classe
	 */
	public LanguageToTalkJPA() {
		super();
	}
	
	/**
	 * Constructor Clau Composta. Realitza el find per una clau composta
	 * @param nif
	 * @param language
	 */
	public LanguageToTalkJPA(UserJPA user, String language) {
        this.user = user;
		this.language = language;
	}

	
	/**
	 * Constructor de la classe per guardar la informació.
	 * 
	 * @param language Idioma 
	 * @param level Nivell
	 * @param description Descripcio
	 * @param acceptPay Boolean accepta pagar
	 */
	public LanguageToTalkJPA(String language, String level, String description, boolean acceptPay) {
		this.language = language;
		this.level = level;
		this.description = description;
		this.acceptPay = acceptPay;
	}

	
	/**
	 * Obte llenguatge 
	 * @return llenguatge
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Setter assigna llenguatge
	 * @param language que vol practicar
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Obte nivell 
	 * @return nivell del llenguatge que vol practicar
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Assigna nivell 
	 * @param level nivell del llenguatge
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Obte descripcio
	 * @return motiu perque vol practicar el llenguatge
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * assigna la descripcio a la classe
	 * @param description motiu perque vol practicar el llenguatge
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter IsAcceptPay
	 * @return si acceptar pagar per practicar el llenguatge
	 */
	public boolean isAcceptPay() {
		return acceptPay;
	}

	/**
	 * Setter AcceptPay
	 * @param acceptPay assigna si vol pagar per practar el llenguatge
	 */
	public void setAcceptPay(boolean acceptPay) {
		this.acceptPay = acceptPay;
	}

	/**
	 * Obte usuari 
	 * @return objecte usuari
	 */
	public UserJPA getUser() {
		return user;
	}

	/**
	 * Setter Assigna usuari
	 * @param user usuari del llenguatge que vol practicar
	 */
	public void setUser(UserJPA user) {
		this.user = user;
	}
	
	/**
	 * Metode toString de la classe
	 */
	public String toString(){
		return this.getLanguage() + " " + this.getLevel() + " " + this.getDescription() + " " + this.isAcceptPay();
	}

}