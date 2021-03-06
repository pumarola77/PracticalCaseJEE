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
 * 
 * @author Grup 6
 * @version 1.0
 *
 */
@ManagedBean(name = "acceptrequest")
@ViewScoped
public class AcceptRequestMBean implements Serializable{

	/**
	 * Obligatori perque la classe implementa serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * EJB AcceptRequestRemote
	 */
	@EJB
	private TalkAppointmentAdminFacadeRemote AcceptRequestRemote;

	/**
	 * Metode que es fa servir per acceptar una petició de conversa
	 * @param talkid identificador cita
	 * @param nif identificador usuari
	 * @return Nom del Facelet
	 * @throws Exception En el cas que no es realitzi actualització correctament
	 */
	public String AcceptRequest(Long talkid, String nif) throws Exception
	{
		try
		{
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);

			AcceptRequestRemote = (TalkAppointmentAdminFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentAdminBean!ejb.TalkAppointmentAdminFacadeRemote");
			AcceptRequestRemote.acceptRequest(talkid, nif);

			return "MyTalkAppointmentsAsProposalView";

		}catch (PersistenceException e) {
			throw e;
		} 
	}
}
