package jpa;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;


/**
 * Classe JPA LanguageToTalk
 */

@Entity
@Table(name="practicalcase.talkappointment")
public class TalkAppointmentJPA implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idtalkapp", nullable=false, columnDefinition="serial")	
	private Long id;
	
	@Column(name = "descrption")
	private String description;
		
	@Column(name = "date")
	private Date date;
	
	@Column(name = "time")
	private Time time;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private TalkStatus status;
	
	@ManyToOne
	@JoinColumn (name="nifpublish")
	private UserJPA userPublish;
	
	@ManyToOne
	@JoinColumn (name="nifsign")	
	private UserJPA userSign;
	
	@ManyToOne
	@JoinColumn (name="location")
	private LocationJPA location;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="nif",  referencedColumnName="nif"),
		@JoinColumn(name="language", referencedColumnName="language")
	})	
	private LanguageToTalkJPA languageToTalk;
	
	//private String userLanguageToTalk;
	
	public TalkAppointmentJPA() {
		super();
	}
	
	public TalkAppointmentJPA(String description, Date date, Time time, TalkStatus status) {
		this.description = description;
		this.date = date;
		this.time = time;
		this.status = status;
	}

	/**
	 * Getters i setters
	 */
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public TalkStatus getStatus() {
		return status;
	}

	public void setStatus(TalkStatus status) {
		this.status = status;
	}

	/*
	 * Methods get/set persistent relationships
	 * JoinColumn fa referencia a la columna amb la qual realitzem el join.
	 */
	public UserJPA getUserPublish() {
		return userPublish;
	}

	public void setUserPublish(UserJPA userPublish) {
		this.userPublish = userPublish;
	}
	
	public UserJPA getUserSign() {
		return userSign;
	}

	public void setUserSign(UserJPA userSign) {
		this.userSign = userSign;
	}
	
	public LocationJPA getLocation() {
		return location;
	}
	
	public void setLocation(LocationJPA location){
		this.location = location;
	}
	
	public String stringLocation(){
		return getLocation().toString();
	}
	
	public LanguageToTalkJPA getLanguageToTalk() {
		return languageToTalk;
	}
	
	public void setLanguageToTalk(LanguageToTalkJPA langToTalk){
		this.languageToTalk = langToTalk;
	}
	
	
}
