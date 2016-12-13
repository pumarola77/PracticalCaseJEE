package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.TalkAppointmentFacadeRemote;


@ManagedBean(name = "RegisterInTalkAppointment")
@SessionScoped
public class RegisterInTalkAppointmentMBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private TalkAppointmentFacadeRemote registerInTalkedAppointmentRemote;
	
	public String registerInTalkedAppointment(/*String nif,*/ Integer talkid) throws Exception {
		
		String nif ="";
		
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == true) 
		{
			nif = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString();
		}	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);

		registerInTalkedAppointmentRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		registerInTalkedAppointmentRemote.registerInTalkAppointment(nif, talkid);
		
		
		return "TalkAppointmentsListView";
	}
	
}
