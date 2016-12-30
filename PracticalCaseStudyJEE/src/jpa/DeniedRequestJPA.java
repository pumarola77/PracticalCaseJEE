package jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * Classe corresponent als motius de cancel.lació de la cita
 * 
 * 
 * @author Grup 6
 * @version 1.0
 *
 */
@Entity
@Table(name="practicalcase.deniedrequest", uniqueConstraints={@UniqueConstraint(columnNames = {"nif", "talkapp"})})
public class DeniedRequestJPA implements Serializable {

	/**
	 * Camp obligatori al implementar serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Descriu la rao per cancel.lar la cita
	 */
	@Column(name = "reason")
	private String reason;
	
	/**
	 * Identificador usuari
	 */
	@Id
	@ManyToOne
	@JoinColumn (name="nif")
	private UserJPA user;
	
	/**
	 * Identificador cita
	 */
	@Id
	@ManyToOne
	@JoinColumn (name="talkapp")
	private TalkAppointmentJPA talkApp;

	/**
	 * Constructor
	 */
	public DeniedRequestJPA() {
		super();
	}
	
	/**
	 * Constructor
	 * @param reason mmotiu de la cancel.lacio
	 */
	public DeniedRequestJPA(String reason) {
		this.reason = reason;
	}
	
	/**
	 * Getters/Setters
	 */
	
	/**
	 * Getter motiu cancel.lació cita
	 * @return la cancel.lació cita
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Setter guarda la rao de cancel.lacio
	 * @param reason descripcio del motiu
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	/**
	 * Obte usuari
	 * @return usuari
	 */
	public UserJPA getUser() {
		return user;
	}

	/**
	 * Informa Usuari
	 * @param user objecte usuari
	 */
	public void setUser(UserJPA user) {
		this.user = user;
	}
	
	/**
	 * Obte cita
	 * @return la cita que conforma la cancel.lacio de la cita
	 */
	public TalkAppointmentJPA getTalkApp(){
		return talkApp;
	}
	
	/**
	 * Informa de la cita que es cancel.la
	 * @param talkApp objecte corresponent a la cita
	 */
	public void setTalkApp(TalkAppointmentJPA talkApp){
		this.talkApp = talkApp;
	}
	
}
