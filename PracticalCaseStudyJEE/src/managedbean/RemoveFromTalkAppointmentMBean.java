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

/**
 * ManagedBean RemoveFromTalkAppointment
 * 
 * @author Grup 6
 * @version 1.0
 *
 */
@ManagedBean(name = "RemoveFromTalkAppointment")
@ViewScoped
public class RemoveFromTalkAppointmentMBean implements Serializable {

	/**
	 * Obligatori perque la classe implementa serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * EJB TalkAppointmentFacadeRemote
	 */
	@EJB
	private TalkAppointmentFacadeRemote removeFromTalkedAppointmentRemote;
	
	/**
	 * Desapunta una cita
	 * 
	 * @param talkid identificador cita
	 * @return vista
	 * @throws Exception incidencia al esborrar cita
	 */
	public String removeFromTalkAppointment(Long talkid) throws Exception {

		String nif ="";
		
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == true) 
		{
			nif = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString();
		}	
		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);

		removeFromTalkedAppointmentRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		removeFromTalkedAppointmentRemote.removeFromTalkAppointment(nif, talkid);

		
		return "MyTalkAppointmentsListView";
	}
	
}
