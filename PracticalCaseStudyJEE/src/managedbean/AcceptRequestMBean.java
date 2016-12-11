package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.PersistenceException;

import ejb.TalkAppointmentAdminFacadeRemote;

/**
 * Managed Bean AcceptRequestMBean
 */
@ManagedBean(name = "acceptrequest")
@SessionScoped
public class AcceptRequestMBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private TalkAppointmentAdminFacadeRemote AcceptRequestRemote;

	/**
	 * Metode que es fa servir per acceptar una petició de conversa
	 * @return Nom del Facelet
	 * @throws Exception
	 */
	public String AcceptRequest(int talkid, String nif) throws Exception
	{
		try
		{
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);

			AcceptRequestRemote = (TalkAppointmentAdminFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentAdminFacadeBean!ejb.TalkAppointmentAdminFacadeRemote");
			AcceptRequestRemote.acceptRequest(talkid, nif);

			return "MyTalkAppointmentsAsProposalView";

		}catch (PersistenceException e) {
			throw e;
		} 
	}
}
