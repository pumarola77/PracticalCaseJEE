package jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Classe JPA TalkedLanguage
 */

@Entity
@Table(name="practicalcase.talkedlanguage",  uniqueConstraints={@UniqueConstraint(columnNames = {"nif", "language"})})
public class TalkedLanguageJPA implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "language")
	private String language;

	@Column(name = "level")
	private String level;

	@Column(name = "description")
	private String description;

	private UserJPA user;

	/**
	 * Constructor
	 */
	public TalkedLanguageJPA() {
		super();
	}

	public TalkedLanguageJPA(String language, String level, String description) {
		this.language = language;
		this.level = level;
		this.description = description;
	}

	/**
	 * Getters i setters dels camps de la base de dades
	 */

	@Id
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
	@Id
	@ManyToOne
	@JoinColumn (name="nif")
	public UserJPA getUser() {
		return user;
	}

	public void setUser(UserJPA user) {
		this.user = user;
	}

}
