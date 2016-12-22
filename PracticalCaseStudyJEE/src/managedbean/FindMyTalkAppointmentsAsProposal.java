package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.TalkAppointmentAdminFacadeRemote;
import jpa.TalkAppointmentJPA;

@ManagedBean(name = "findmytalkappasproposal")
@SessionScoped
public class FindMyTalkAppointmentsAsProposal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String errorFormulari; //Aquest parametre serveix per mostrar un error a la propia pàgina del formulari

	/**
	 * Correspont al nif del usuari registrat
	 */
	private String nif;

	@EJB
	private TalkAppointmentAdminFacadeRemote findTalkAppointmentsAdminRemote;


	/* Variable per controlar els registres que esta veient un usuari */
	private int screen = 0;
	protected int numberTalkAppointments = 0;

	/**
	 * store all the instances of TalkAppointments
	 */
	private Collection<TalkAppointmentJPA> talkAppointmentsList;

	/**
	 * store 10 instances of TalkAppointments 
	 */
	protected Collection<TalkAppointmentJPA> talkAppointmentsListView;

	/**
	 * Obten el nif de l'usuari registrat
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Setter del nif del usuari registrat
	 * @param nif
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public String getErrorFormulari(){
		return errorFormulari;
	}
	public void setErrorFormulari (String errorFormulari){
		this.errorFormulari = errorFormulari;
	}

	/**
	 * Constructor de la classe
	 * @throws Exception
	 */
	public FindMyTalkAppointmentsAsProposal() throws Exception
	{
		this.talkAppointmentsList();
	}

	/**
	 * Carrega la llista de cites propossades per l'usuari
	 * @return la vista de la pantalla del llistat
	 * @throws Exception
	 */
	public String listTalkAppointments() throws Exception {				
		talkAppointmentsList();				
		return "MyTalkAppointmentsAsProposalView";
	}




	/**
	 * Carrega les propostes creades per l'usuari registrat
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void talkAppointmentsList() throws Exception {		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);	

		//Carregar el nif
		this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());	

		screen = 0;
		findTalkAppointmentsAdminRemote = (TalkAppointmentAdminFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentAdminBean!ejb.TalkAppointmentAdminFacadeRemote");
		talkAppointmentsList = (Collection<TalkAppointmentJPA>) findTalkAppointmentsAdminRemote.findMyTalkAppointmentsAsProposal(this.getNif());		
	}

	/**
	 * Metode que retorna una llista amb les cites publicades per un usuari.
	 * @return Retorna la llista 10 anteriors / 10 seguents
	 * @throws Exception
	 */
	public Collection<TalkAppointmentJPA> getTalkAppointmentsListView() throws Exception 
	{		
		int n=0;		
		talkAppointmentsListView = new ArrayList<TalkAppointmentJPA>();
		for (Iterator<TalkAppointmentJPA> iter2 = talkAppointmentsList.iterator(); iter2.hasNext();)
		{
			TalkAppointmentJPA aux = (TalkAppointmentJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.talkAppointmentsListView.add(aux);
			}
			n +=1;
		}

		// anteriorment nomes havia return talkAppointmentsList;
		this.numberTalkAppointments = n;
		return talkAppointmentsListView;
	}


	/**
	 * Avança 10 posicions els registres de la llista
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < talkAppointmentsList.size()))
		{
			screen +=1;
		}
	}

	/**
	 * Retrocedeix 10 posicions el registre de la llista
	 */
	public void previousScreen()
	{
		if ((screen > 0))
		{
			screen -=1;
		}
	}

	/**
	 * 
	 */



}
