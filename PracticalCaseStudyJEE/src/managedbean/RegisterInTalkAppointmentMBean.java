package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.TalkAppointmentFacadeRemote;
import jpa.TalkAppointmentJPA;


@ManagedBean(name = "RegisterInTalkAppointment")
@ViewScoped
public class RegisterInTalkAppointmentMBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private TalkAppointmentFacadeRemote registerInTalkedAppointmentRemote;
	
	private TalkAppointmentJPA talkAppointment;
	private Long id;
	private String nif;
	
	public RegisterInTalkAppointmentMBean() {
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == true) 
		{
			this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());
		}		
	}
	
	public String registerInTalkedAppointment(Long talkid) throws Exception {
						
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		
		registerInTalkedAppointmentRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		registerInTalkedAppointmentRemote.registerInTalkAppointment(this.getNif(), talkid);
		
		
		//return "TalkAppointmentsListView";
		return null;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getNif() {
		return nif;
	}
	
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public TalkAppointmentJPA getTalkAppointment(){
		return talkAppointment;
	}
	
	public void setTalkAppointment(Long id)  throws Exception{
		talkAppointment = (TalkAppointmentJPA) registerInTalkedAppointmentRemote.showTalkAppointment(id);		
	}
	
	public void showRegister() throws Exception {		
		this.setTalkAppointment(this.getId());
	}
	
}
