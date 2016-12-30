package jpa;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;


/**
 * Identifica cites
 * 
 * @author Bazinga
 * @since 1.0
 */
@Entity
@Table(name="practicalcase.talkappointment")
public class TalkAppointmentJPA implements Serializable{

	/**
	 * Obligatori perque la classe implementa serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador
	 */
	@Id
	@Column(name = "idtalkapp", nullable=false, columnDefinition="serial")	
	private Long id;
	
	/**
	 * Descripcio de la cita
	 * 
	 */
	@Column(name = "descrption")
	private String description;
		
	/**
	 * Data de la cita
	 */
	@Column(name = "date")
	private Date date;
	
	/**
	 * hora de la cita
	 */
	@Column(name = "time")
	private Time time;
	
	/**
	 * estat de la cita
	 */
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private TalkStatus status;
	
	/**
	 * Relació amb usuari que publica la cita
	 */
	@ManyToOne
	@JoinColumn (name="nifpublish")
	private UserJPA userPublish;
	
	/**
	 * Relació amb usuari que s'apunta a la cita
	 */
	@ManyToOne
	@JoinColumn (name="nifsign")	
	private UserJPA userSign;
	
	/**
	 * Relació amb la localització de la cita
	 */
	@ManyToOne
	@JoinColumn (name="location")
	private LocationJPA location;
	
	/**
	 * Identificació de la llengua de la cita
	 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="nif",  referencedColumnName="nif"),
		@JoinColumn(name="language", referencedColumnName="language")
	})	
	private LanguageToTalkJPA languageToTalk;
	
	
	/**
	 * Constructor
	 */
	public TalkAppointmentJPA() {
		super();
	}
	
	/**
	 * Constructor de la classe amb parametres
	 * 
	 * @param description descripcio de la cita
	 * @param date data de la cita
	 * @param time hora de la cita
	 * @param status estat de la cita
	 */
	public TalkAppointmentJPA(String description, Date date, Time time, TalkStatus status) {
		this.description = description;
		this.date = date;
		this.time = time;
		this.status = status;
	}

	
	/**
	 * Getter Identificador cita
	 * @return identificador de la cita
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter Assgina identificador de la cita
	 * 
	 * @param id identificador de la cita
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter Obte la descripció
	 * 
	 * @return la descripcio assignada a la cita
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter retorna la descripcio
	 * 
	 * @param description descripcio de la cita
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Obte data de la cita
	 * 
	 * @return data de la cita
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Assigna data de la cita
	 * @param date de la cita
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Obte hora de la cita
	 * @return hora de la cita
	 */
	public Time getTime() {
		return time;
	}

	/**
	 * Assigna hora de la cita
	 * @param time hora de la cita
	 */
	public void setTime(Time time) {
		this.time = time;
	}

	/**
	 * Getter status
	 * @return estat de la cita
	 */
	public TalkStatus getStatus() {
		return status;
	}

	/**
	 * assigna estat a la cita
	 * @param status de la cita
	 */
	public void setStatus(TalkStatus status) {
		this.status = status;
	}

	/**
	 * Getter Usuari que ha publicat la cita
	 * @return usuari que ha publicat la cita
	 */
	public UserJPA getUserPublish() {
		return userPublish;
	}

	/**
	 * Setter Assigna usuari que ha publicat la cita
	 * @param userPublish usuari que ha publicat la cita
	 */
	public void setUserPublish(UserJPA userPublish) {
		this.userPublish = userPublish;
	}
	
	/**
	 * Getter usuari que s'ha apuntat a la cita
	 * @return usuari que s'ha apuntat a la cita
	 */
	public UserJPA getUserSign() {
		return userSign;
	}

	/**
	 * Setter usuari que s'ha apuntat a la cita
	 * @param userSign usuari inscrita a la cita
	 */
	public void setUserSign(UserJPA userSign) {
		this.userSign = userSign;
	}
	
	/**
	 * Getter lloc on es produeix la cita
	 * @return lloc de la cita
	 */
	public LocationJPA getLocation() {
		return location;
	}
	
	/**
	 * Setter assigna el lloc de la cita
	 * @param location lloc de la cita
	 */
	public void setLocation(LocationJPA location){
		this.location = location;
	}
	
	/**
	 * Descripcio de la localitzacio
	 * @return Retorna una descripcio de la localitzacio
	 */
	public String stringLocation(){
		return getLocation().toString();
	}
	
	/**
	 * Obte llenguatges de la cita
	 * @return llenguatges de la cita
	 */
	public LanguageToTalkJPA getLanguageToTalk() {
		return languageToTalk;
	}
	
	/**
	 * Assigna llenguatges de la cita
	 * @param langToTalk llenguatges de la cita
	 */
	public void setLanguageToTalk(LanguageToTalkJPA langToTalk){
		this.languageToTalk = langToTalk;
	}
	
	
}
