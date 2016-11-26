package jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Classe JPA TalkedLanguage
 */

@Entity
@Table(name="practicalcase.talkedlanguage")
public class TalkedLanguageJPA implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int    id;
	private String language;
	private String level;
	private String description;
	private UserJPA user;
	
	/**
	 * Constructor
	 */
	public TalkedLanguageJPA() {
		super();
	}
	
	public TalkedLanguageJPA(Integer id , String language, String level, String description) {
		this.id = id;
		this.language = language;
		this.level = level;
		this.description = description;
	}
	
	/**
	 * Getters i setters dels camps de la base de dades
	 */
	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		
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
	
	/*
	 * Methods get/set persistent relationships
	 * JoinColumn fa referencia a la columna amb la qual realitzem el join.
	 */
	@ManyToOne
	@JoinColumn (name="nif")
	public UserJPA getUser() {
		return user;
	}
	
	public void setUser(UserJPA user) {
		this.user = user;
	}
	
}
