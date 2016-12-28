package jpa;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Classe JPA DeniedRequest
 */

@Entity
@Table(name="practicalcase.deniedrequest", uniqueConstraints={@UniqueConstraint(columnNames = {"nif", "talkapp"})})
public class DeniedRequestJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "reason")
	private String reason;
	
	@Id
	@ManyToOne
	@JoinColumn (name="nif")
	private UserJPA user;
	
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
	
	public DeniedRequestJPA(String reason) {
		this.reason = reason;
	}
	
	/**
	 * Getters/Setters
	 */
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
	

	public TalkAppointmentJPA getTalkApp(){
		return talkApp;
	}
	
	public void setTalkApp(TalkAppointmentJPA talkApp){
		this.talkApp = talkApp;
	}
	
}
