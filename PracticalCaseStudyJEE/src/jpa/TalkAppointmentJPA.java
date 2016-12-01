package jpa;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;
import javax.xml.stream.Location;



/**
 * Classe JPA LanguageToTalk
 */

@Entity
@Table(name="practicalcase.talkappointment")
public class TalkAppointmentJPA implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	private int id;
	
	@Column(name = "descrption")
	private String description;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "time")
	private Time time;
	
	@Column(name = "status")
	private TalkStatus status;
	
	private UserJPA userPublish;
	private UserJPA userSign;

	public TalkAppointmentJPA() {
		super();
	}
	
	public TalkAppointmentJPA(int id, String description, String location, Date date, Time time, TalkStatus status) {
		this.id = id;
		this.description = description;
		this.location = location;
		this.date = date;
		this.time = time;
		this.status = status;
	}

	/**
	 * Getters i setters
	 */
	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
	@ManyToOne
	@JoinColumn (name="nifpublish")
	public UserJPA getUserPublish() {
		return userPublish;
	}

	public void setUserPublish(UserJPA userPublish) {
		this.userPublish = userPublish;
	}
	
	@ManyToOne
	@JoinColumn (name="nifsign")
	public UserJPA getUserSign() {
		return userSign;
	}

	public void setUserSign(UserJPA userSign) {
		this.userPublish = userSign;
	}
	
}