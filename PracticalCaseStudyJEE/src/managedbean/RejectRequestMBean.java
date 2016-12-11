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
 * Managed Bean RejectRequestMBean
 */
@ManagedBean(name = "rejectrequest")
@SessionScoped
public class RejectRequestMBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private TalkAppointmentAdminFacadeRemote RejectRequestRemote;

	/**
	 * Metode que es fa servir per rebutjar una petició de conversa
	 * @return Nom del Facelet
	 * @throws Exception
	 */
	public String rejectRequest(int talkid, String nif, String reason) throws Exception
	{
		try
		{
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);

			RejectRequestRemote = (TalkAppointmentAdminFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentAdminFacadeBean!ejb.TalkAppointmentAdminFacadeRemote");
			RejectRequestRemote.rejectRequest(talkid, nif, reason);

			return "MyTalkAppointmentsAsProposalView";

		}catch (PersistenceException e) {
			throw e;
		} 
	}
}
