package managedbean;

import java.io.Serializable;
import java.sql.Time;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
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

	/* Llistat complert de cites (publicades/apuntades) de l'usuari*/
	private Collection<TalkAppointmentJPA> myTalkAppointmentsFullList;
	/* Les 10 cites (publicades/apuntades) o menys que s'hagin de mostrar per pantalla*/
	protected Collection<TalkAppointmentJPA> myTalkAppointmentsListView;
	/* Aquestes variables fan de contador per saber quin punt de la llista de cites (publicades/apuntades) esta veient l'usuari*/
	private int screen;

	/*
	 * Serveixen per guardar els valors dels desplegables
	 */
	//protected Collection<SelectItem> datesList = new ArrayList<SelectItem>();
	//protected Collection<SelectItem> tipusList = new ArrayList<SelectItem>();

	//private String dates = "ALL DATES";
	//private String tipus = "PUBLICADES";

	/*
	 * Retornen els valors del ejb que es mostrarant al desplegable
	 */
	//private Collection<Date> fechasList;
	//private Collection<String> tiposList;

	@EJB
	private TalkAppointmentFacadeRemote findMyTalkAppointmentsRemote;

	public FindMyTalkAppointmentsMBean() throws Exception 
	{		
		this.myTalkAppointmentsFullList();
	}

	/*
	public Collection<SelectItem> getDatesList() {
		return datesList;
	}

	public Collection<SelectItem> getTipusList() {
		return tipusList;
	}

	public String getDates() {
		return this.dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getTipus() {
		return this.tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}
	*/

	public String getNif(){
		return nif;
	}
	public void setNif(String nif){
		this.nif= nif;
	}

	/*
	public void datesValueChanged(ValueChangeEvent datesChanged) {				
		this.setDates(datesChanged.getNewValue().toString());
	}

	public void tipussValueChanged(ValueChangeEvent tipusChanged) {				
		this.setTipus(tipusChanged.getNewValue().toString());
	}
	*/
	
	public String listMyTalkAppointments() throws Exception {		
		myTalkAppointmentsFullList();		
		return "MyTalkAppointmentsListView";
	}

	public void previousScreen()
	{
		if ((screen > 0))
		{
			screen -=1;
		}
	}

	public void nextScreen()
	{
		if (((screen+1)*10 < myTalkAppointmentsFullList.size()))
		{
			screen +=1;
		}
	}

	/**
	 * Metode que es fa servir per obtenir els TalkAppointments publicats per un usuari
	 * @return col·leccio de cites
	 * @throws Exception
	 */
	public Collection<TalkAppointmentJPA> getMyTalkAppointmentsListView() throws Exception{

		int n=0;

		// S'utilitza per carregar el llistat complert de cites de l'usuari	
		this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		findMyTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		myTalkAppointmentsFullList = findMyTalkAppointmentsRemote.findMyTalkAppointments(this.getNif(), "", "PUBLICADES");//(this.getNif(),this.getDates(), this.getTipus());

		// Carrega el llistat de cites reduit que s'ha de mostrar
		myTalkAppointmentsListView = new ArrayList<TalkAppointmentJPA>();

		Iterator<TalkAppointmentJPA> iter = myTalkAppointmentsFullList.iterator();
		while(iter.hasNext())
		{
			TalkAppointmentJPA aux = (TalkAppointmentJPA) iter.next();
			if (n>= screen*10 && n< (screen*10+10))
			{
				this.myTalkAppointmentsListView.add(aux);
			}
			n=n+1;
		}
		return myTalkAppointmentsListView;
	}

	/*
	 * Metode que es crida en el boto "confirm" dins de la vista
	 * FindMyTalkAppointmentsView.
	 * S'haura de passar la data, i el tipus de petició de cita
	 */
	@SuppressWarnings("unchecked")
	private void myTalkAppointmentsFullList() throws Exception {		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);	

		screen = 0;
		findMyTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		myTalkAppointmentsFullList = (Collection<TalkAppointmentJPA>) findMyTalkAppointmentsRemote.findMyTalkAppointments(this.getNif(), "", "PUBLICADES");//(this.getNif(),this.getDates(), this.getTipus());		
	}

}
