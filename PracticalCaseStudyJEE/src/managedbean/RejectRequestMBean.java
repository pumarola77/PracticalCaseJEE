package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import ejb.TalkAppointmentAdminFacadeRemote;
import ejb.TalkAppointmentFacadeRemote;
import jpa.TalkAppointmentJPA;

/**
 * Managed Bean RejectRequestMBean
 */
@ManagedBean(name = "rejectrequest")
@SessionScoped
public class RejectRequestMBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private int success; //Aquest parametre serveix per controlar si el formulari de registre conte errors en les dades introduides
	protected String errorFormulari; //Aquest parametre serveix per mostrar un error a la propia pàgina del formulari

	private int talkId;
	private String nif;

	@EJB
	private TalkAppointmentAdminFacadeRemote RejectRequestRemote;

	public int getTalkId(){
		return talkId;
	}

	public void setTalkId(int talkId){
		this.talkId = talkId;
	}

	public String getNif(){
		return nif;
	}

	public void setNif(String nif){
		this.nif = nif;
	}

	public String getErrorFormulari(){
		return errorFormulari;
	}
	public void setErrorFormulari (String errorFormulari){
		this.errorFormulari = errorFormulari;
	}

	/**
	 * Metode que es fa servir per rebutjar una petició de conversa
	 * @return Nom del Facelet
	 * @throws Exception
	 */
	public String rejectRequest(int talkid, String nif) throws Exception
	{
		try
		{
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();

			String reason = request.getParameter("rejectRequestForm:motiu");

			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);

			RejectRequestRemote = (TalkAppointmentAdminFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentAdminBean!ejb.TalkAppointmentAdminFacadeRemote");
			success = RejectRequestRemote.rejectRequest(talkid, nif, reason);

			// Si el registre de l'usuari no es pot realitzar es perque el NIF amb l'idioma ja esta introduit a la base de dades, per tant es mostra error
			if (success==1) // El codi d'error 1 indica que ja existeix el nif amb l'idioma a la base de dades
			{
				errorFormulari = "ERROR:La petició de conversa no existeix.";
				return "RejectRequestView"; //Es retorna el nom de la vista a la que volem que ens redirigim, en aquest cas la mateixa			
			}
			else if (success==2)
			{
				errorFormulari = "ERROR: L'usuari amb nif: " + nif + " no existeix.";
				return "RejectRequestView"; //Es retorna el nom de la vista a la que volem que ens redirigim, en aquest cas la mateixa
			}
			else // Si success es diferent de 1 vol dir que la operació s'ha dut a terme correctament
			{
				return "MyTalkAppointmentsAsProposalView"; //Si la introducció de l'usuari es correcta es retorna la vista LanguagesToTalkListView.xhtml per a que automaticament es redireccioni cap alla
			}

		}catch (PersistenceException e) {
			throw e;
		} 
	}

	public String rejectRequestView(int talkid, String nif) throws Exception{
		this.setTalkId(talkid);
		this.setNif(nif);
		if (nif == ""){
			errorFormulari = "ERROR: No hi ha cap usuari apuntat a la petició de conversa..";
			return "MyTalkAppointmentsAsProposalView";
		}
		else{
			return "RejectRequestView";	
		}
	}
}
