package jpa;
import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

/**
 * Usuaris
 * 
 * @author Grup 6
 * @since 1.0
 *
 */
@Entity
@Table(name="practicalcase.user",  uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class UserJPA implements Serializable {

	/**
	 * Obligatori implementar perque la classe es serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador
	 */
	@Id
	@Column(name="nif")
	private String nif;
	
	/**
	 * nom
	 */
	@Column(name="name")
	private String name;
	
	/**
	 * Cognom
	 */
	@Column(name="surname")
	private String surname;
	
	/**
	 * Telefon
	 */
	@Column(name="phone")
	private String phone;
	
	/**
	 * Password
	 */
	@Column(name="password")
	private String password;
	
	/**
	 * Email
	 */
	@Column(name="email")
	private String email;
	
	/**
	 * Llengues que parla
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn(name = "nif")
	private Collection<TalkedLanguageJPA> talkedLanguages;
	
	/**
	 * Constructors
	 */
	public UserJPA() {
		super();
	}
	
	/**
	 * Constructor amb variables
	 * 
	 * @param nif identificador usuari
	 * @param name nom
	 * @param surname cognom
	 * @param phone telefon 
	 * @param password password
	 * @param email email
	 */
	public UserJPA(String nif, String name, String surname, String phone, String password, String email) {		
		this.nif = nif;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.password = password;
		this.email = email;
	}


	/**
	 * Getter identificador
	 * @return identificador
	 */
	public String getNif() {
		return nif;
	}
	
	/**
	 * Setter nif
	 * @param nif nif
	 */	
	public  void setNif(String nif) {
		this.nif = nif;
	}
	
	
	/**
	 * Getter nom
	 * @return nom
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter nom
	 * @param name nom
	 */	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter cognom
	 * @return cognom
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * Setter cognom
	 * @param surname cognom
	 */	
	public  void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 * Getter telefon
	 * @return telefon
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * Setter telefon
	 * @param phone telefon
	 */	
	public  void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * Getter password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Setter password
	 * @param password password
	 */	
	public  void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Getter email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter email
	 * @param email email
	 */	
	public  void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter Llenguatges parlats per usuari
	 * @return llenguatges parlats per usuari
	 */
	public Collection<TalkedLanguageJPA> getTalkedLanguageByUser() {
		return talkedLanguages;
	}
	
	/**
	 * Setter Llenguatges parlats per usuari
	 * @param talkedlanguages llenguatges parlat per usuari
	 */
	public void setTalkedLanguageByUser(Collection<TalkedLanguageJPA> talkedlanguages) {
		this.talkedLanguages = talkedlanguages;
	}
	
	
}
