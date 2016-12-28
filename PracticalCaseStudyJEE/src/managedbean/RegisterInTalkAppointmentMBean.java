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
	private int id;
	
	public RegisterInTalkAppointmentMBean() {
		
	}
	
	public String registerInTalkedAppointment(Long talkid) throws Exception {
						
		String nif ="";
		
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == true) 
		{
			nif = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString();
		}	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);

		registerInTalkedAppointmentRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		registerInTalkedAppointmentRemote.registerInTalkAppointment(nif, talkid);
		
		
		//return "TalkAppointmentsListView";
		return null;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public TalkAppointmentJPA getTalkAppointment(){
		return talkAppointment;
	}
	
	public void setTalkAppointment(Long id)  throws Exception{
		talkAppointment = (TalkAppointmentJPA) registerInTalkedAppointmentRemote.showTalkAppointment(id);		
	}
	
	public String showRegister(Long id) throws Exception {
		this.setTalkAppointment(id);
		return "RegisterInTalkAppointmentView";
	}
	
}
