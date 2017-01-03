package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.TalkAppointmentFacadeRemote;
import jpa.TalkAppointmentJPA;

/**
 * ManagedBean RegisterInTalkAppointment
 * 
 * @author Bazinga
 * @version 1.0
 */
@ManagedBean(name = "RegisterInTalkAppointment")
@ViewScoped
public class RegisterInTalkAppointmentMBean implements Serializable {

	/**
	 * Obligatori perque la classe implementa serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * EJB TalkAppointmentFacadeRemote
	 */
	@EJB
	private TalkAppointmentFacadeRemote registerInTalkedAppointmentRemote;
	
	/**
	 * Talkappointment
	 */
	private TalkAppointmentJPA talkAppointment;
	
	/**
	 * Identificador
	 */
	private Long id;
	
	/**
	 * Identificador usuari
	 */
	private String nif;
	
	/**
	 * Constructor
	 */
	public RegisterInTalkAppointmentMBean() {
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == true) 
		{
			this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());
		}		
	}
	
	/*public String registerInTalkedAppointment(/*Long talkid) throws Exception {*/
	/**
	 * Registrar usuari
	 * @return vista
	 * @throws Exception incidencia al registrar usuari
	 */
	public String register() throws Exception {					
		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		
		registerInTalkedAppointmentRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		Boolean registerValid = registerInTalkedAppointmentRemote.registerInTalkAppointment(this.getNif(), this.getId());
		
		if (registerValid==true)
			{
				return "TalkAppointmentsListView";
			}
		else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error! No pots apuntar-te a aquesta cita perquè no parles aquest llenguatge!"));
				return null;
			}
		
		//return "TalkAppointmentsListView";
		//return null;
	}
	
	/**
	 * Getter id
	 * @return id
	 */
	public Long getId(){
		return id;
	}
	
	/**
	 * Setter id
	 * @param id identificador
	 */
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * Getter nif
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}
	
	/**
	 * Setter nif
	 * @param nif identificador nif
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	/**
	 * Getter TalkAppointment
	 * @return talkappointment
	 */
	public TalkAppointmentJPA getTalkAppointment(){
		return talkAppointment;
	}
	
	/**
	 * Setter TalkAppointment
	 * @param id identificador
	 * @throws Exception Registrar cita
	 */
	public void setTalkAppointment(Long id)  throws Exception{
		talkAppointment = (TalkAppointmentJPA) registerInTalkedAppointmentRemote.showTalkAppointment(id);		
	}
	
	/**
	 * showRegister
	 * @throws Exception mostrar cita
	 */
	public void showRegister() throws Exception {		
		this.setTalkAppointment(this.getId());
	}
	
}
