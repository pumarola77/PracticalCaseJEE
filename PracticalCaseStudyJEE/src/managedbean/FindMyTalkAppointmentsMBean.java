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

import ejb.TalkAppointmentFacadeRemote;
import jpa.TalkAppointmentJPA;

/**
 * Managed Bean FindMyTalkAppointments
 */
@ManagedBean(name = "mytalkappointment")
@SessionScoped
public class FindMyTalkAppointmentsMBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nif;

	/* Aquestes variables contindran el llistat complert de cites (publicades/apuntades) de l'usuari*/
	private Collection<TalkAppointmentJPA> myTalkAppointmentsPublishFullList;
	private Collection<TalkAppointmentJPA> myTalkAppointmentsSignFullList;
	/* Aquestes variables contindran les 10 cites (publicades/apuntades) o menys que s'hagin de mostrar per pantalla*/
	protected Collection<TalkAppointmentJPA> myTalkAppointmentsPublishListView;
	protected Collection<TalkAppointmentJPA> myTalkAppointmentsSignListView;
	/* Aquestes variables fan de contador per saber quin punt de la llista de cites (publicades/apuntades) esta veient l'usuari*/
	private int publishScreen;
	private int signScreen;

	@EJB
	private TalkAppointmentFacadeRemote findMyTalkAppointmentsRemote;

	public String getNif(){
		return nif;
	}
	public void setNif(String nif){
		this.nif= nif;
	}

	public void previousPublishScreen()
	{
		if ((publishScreen > 0))
		{
			publishScreen -=1;
		}
	}
	public void previousSignScreen()
	{
		if ((signScreen > 0))
		{
			signScreen -=1;
		}
	}
	
	public void nextPublishScreen()
	{
		if (((publishScreen+1)*10 < myTalkAppointmentsPublishFullList.size()))
		{
			publishScreen +=1;
		}
	}
	public void nextSignScreen()
	{
		if (((signScreen+1)*10 < myTalkAppointmentsSignFullList.size()))
		{
			signScreen +=1;
		}
	}	

	/**
	 * Metode que es fa servir per obtenir els TalkAppointments publicats per un usuari
	 * @return col·leccio de cites
	 * @throws Exception
	 */
	public Collection<TalkAppointmentJPA> getMyTalkAppointmentsPublishListView() throws Exception{

		int n=0;

		// S'utilitza per carregar el llistat complert d'idiomes de l'usuari	
		this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		findMyTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		myTalkAppointmentsPublishFullList = findMyTalkAppointmentsRemote.findMyTalkAppointments(this.getNif(), true);
				
		// Carrega el llistat de cites reduit que s'ha de mostrar
		myTalkAppointmentsPublishListView = new ArrayList<TalkAppointmentJPA>();

		Iterator<TalkAppointmentJPA> iter = myTalkAppointmentsPublishFullList.iterator();
		while(iter.hasNext())
		{
			TalkAppointmentJPA aux = (TalkAppointmentJPA) iter.next();
			if (n>= publishScreen*10 && n< (publishScreen*10+10))
			{
				this.myTalkAppointmentsPublishListView.add(aux);
			}
			n=n+1;
		}
		return myTalkAppointmentsPublishListView;
	}

	/**
	 * Metode que es fa servir per obtenir les cites a les que s'ha apuntat un usuari
	 * @return Col·leccio de cites
	 * @throws Exception
	 */
	public Collection<TalkAppointmentJPA> getMyTalkAppointmentsSignListView() throws Exception{

		int n=0;

		// S'utilitza per carregar el llistat complert de cites apuntades de l'usuari	
		this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		findMyTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		myTalkAppointmentsSignFullList = findMyTalkAppointmentsRemote.findMyTalkAppointments(this.getNif(), false);
				
		// Carrega el llistat de cites reduit que s'ha de mostrar
		myTalkAppointmentsSignListView = new ArrayList<TalkAppointmentJPA>();

		Iterator<TalkAppointmentJPA> iter = myTalkAppointmentsSignFullList.iterator();
		while(iter.hasNext())
		{
			TalkAppointmentJPA aux = (TalkAppointmentJPA) iter.next();
			if (n>= signScreen*10 && n< (signScreen*10+10))
			{
				this.myTalkAppointmentsSignListView.add(aux);
			}
			n=n+1;
		}
		return myTalkAppointmentsSignListView;
	}

}
