package jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * 
 * Implementacio llengues que parla
 * 
 * @author Grup6
 * @since 1.0
 *
 */
@Entity
@Table(name="practicalcase.talkedlanguage",  uniqueConstraints={@UniqueConstraint(columnNames = {"nif", "language"})})
public class TalkedLanguageJPA implements Serializable{

	/**
	 * Obligatori perque la classe implementa serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador
	 */
	@Id
	@Column(name = "language")
	private String language;

	/**
	 * Nivell
	 */
	@Column(name = "level")
	private String level;

	/**
	 * Descripcio
	 */
	@Column(name = "description")
	private String description;

	/**
	 * Identificador usuari
	 */
	@Id
	@ManyToOne
	@JoinColumn (name="nif")
	private UserJPA user;

	/**
	 * Constructor
	 */
	public TalkedLanguageJPA() {
		super();
	}

	/**
	 * Constructor amb parametres
	 * 
	 * @param language llenguatge
	 * @param level nivell
	 * @param description descripcio
	 */
	public TalkedLanguageJPA(String language, String level, String description) {
		this.language = language;
		this.level = level;
		this.description = description;
	}


	/**
	 * Obte la llengua que parla
	 * @return llengua parla
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Assigna llengua que parla
	 * @param language llengua que parla
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Getter Obte nivell del llenguatge que parla
	 * @return nivell del llenguatge
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Setter Nivell del llenguatge que parla
	 * @param level retorna el nivell del llenguatge que parla
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Getter Descripcio
	 * @return descripcio assignada al llenguatge que parla
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter Descripcio
	 * @param description descripcio assignada al llenguatge que parla
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter Usuari assignat a la llengua
	 * @return objecte usuari
	 */
	public UserJPA getUser() {
		return user;
	}

	/**
	 * Setter Assigna usuari
	 * @param user usuari llenguatge
	 */
	public void setUser(UserJPA user) {
		this.user = user;
	}

}
