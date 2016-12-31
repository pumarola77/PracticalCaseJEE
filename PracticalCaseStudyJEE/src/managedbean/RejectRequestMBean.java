package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import ejb.TalkAppointmentAdminFacadeRemote;


/**
 * Managed Bean RejectRequestMBean
 */
@ManagedBean(name = "rejectrequest")
@ViewScoped
public class RejectRequestMBean implements Serializable{

	/**
	 * Obligatori perque la classe implementa Serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable per mostrar missatge Error
	 */
	protected String errorFormulari; //Aquest parametre serveix per mostrar un error a la propia pàgina del formulari

	/**
	 * Identificador cita
	 */
	private Long talkId;
	
	/**
	 * Identificador nif
	 */
	private String nif;

	/**
	 * EJB Per la crida als metodes 
	 */
	@EJB
	private TalkAppointmentAdminFacadeRemote RejectRequestRemote;

	/**
	 * Getter Identificador cita
	 * @return identificador de la cita
	 */
	public Long getTalkId(){
		return talkId;
	}

	/**
	 * Setter Identificador de la cita
	 * @param talkId
	 */
	public void setTalkId(Long talkId){
		this.talkId = talkId;
	}

	/**
	 * Getter Identificador usuari nif
	 * @return
	 */
	public String getNif(){
		return nif;
	}

	/**
	 * Setter Identificador usuari
	 * @param nif identificador usuari
	 */
	public void setNif(String nif){
		this.nif = nif;
	}

	/**
	 * Getter Indicencia Formulari
	 * @return incidencia formulari
	 */
	public String getErrorFormulari(){
		return errorFormulari;
	}
	
	/**
	 * Setter Indicencia Formulari
	 * @param errorFormulari Incidencia Formulari
	 */
	public void setErrorFormulari (String errorFormulari){
		this.errorFormulari = errorFormulari;
	}
	
	
	/**
	 * Constructor : 
	 */
	public RejectRequestMBean() throws Exception {
		//Carrega el NIF del User
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == true) {
			this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());
		}
	}

	
	/**
	 * Metode que es fa servir per rebutjar una petició de conversa
	 * 
	 * @param talkid identificador cita
	 * @param nif identificador usuari
	 * @return Nom del Facelet
	 * @throws Exception Indicencia al actualitzar registre.
	 */
	public String rejectRequest(Long talkid, String nif) throws Exception
	{

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();

		String reason = request.getParameter("rejectRequestForm:motiu");

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		
		// Els controls del talkid i el nif es realitzen en el bean. Com ve del llistat
		// Aquests valors en principi han de ser correctes.
				
		RejectRequestRemote = (TalkAppointmentAdminFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentAdminBean!ejb.TalkAppointmentAdminFacadeRemote");
		RejectRequestRemote.rejectRequest(this.getTalkId(), this.getNif(), reason);
		
		return "MyTalkAppointmentsAsProposalView";

	}
	
	/*
	public String rejectRequest(Long talkid, String nif) throws Exception
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
	

	public String rejectRequestView(Long talkid, String nif) throws Exception{
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
	*/
}
