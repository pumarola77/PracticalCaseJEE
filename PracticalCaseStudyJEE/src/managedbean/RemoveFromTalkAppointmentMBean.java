package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.TalkAppointmentFacadeRemote;

@ManagedBean(name = "RemoveFromTalkAppointment")
@SessionScoped
public class RemoveFromTalkAppointmentMBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private TalkAppointmentFacadeRemote removeFromTalkedAppointmentRemote;
	
	public String removeFromTalkAppointment(String nif, Integer talkid) throws Exception {
		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);

		removeFromTalkedAppointmentRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		removeFromTalkedAppointmentRemote.removeFromTalkAppointment(nif, talkid);

		
		return "vista";
	}
	
}
