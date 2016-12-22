package jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Classe JPA LanguageToTalk
 */

@Entity
//@Table(name="practicalcase.languagetotalk",  uniqueConstraints={@UniqueConstraint(columnNames = {"nif", "language"})})
@Table(name="practicalcase.languagetotalk")
public class LanguageToTalkJPA implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "language")
	private String language;

	@Column(name = "level")
	private String level;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "acceptPay")
	private boolean acceptPay;
	
	@Id
	@ManyToOne
	@JoinColumn (name="nif")
	private UserJPA user;

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

	
	public LanguageToTalkJPA(String language, String level, String description, boolean acceptPay) {
		this.language = language;
		this.level = level;
		this.description = description;
		this.acceptPay = acceptPay;
	}

	
	/**
	 * Getters i setters
	 */
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAcceptPay() {
		return acceptPay;
	}

	public void setAcceptPay(boolean acceptPay) {
		this.acceptPay = acceptPay;
	}

	/*
	 * Methods get/set persistent relationships
	 * JoinColumn fa referencia a la columna amb la qual realitzem el join.
	 */
	public UserJPA getUser() {
		return user;
	}

	public void setUser(UserJPA user) {
		this.user = user;
	}
	
	public String toString(){
		return this.getLanguage() + " " + this.getLevel() + " " + this.getDescription() + " " + this.isAcceptPay();
	}

}