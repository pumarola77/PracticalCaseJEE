package managedbean;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.TalkAppointmentFacadeRemote;
import jpa.TalkAppointmentJPA;

@ManagedBean(name = "findmytalkappointments")
@SessionScoped
public class FindMyTalkAppointmentsMBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private TalkAppointmentFacadeRemote findMyTalkAppointmentsRemote;

	/* Variable per controlar els registres que esta veient un usuari */
	private int screen = 0;
	private String nif = "";
	protected int numberTalkAppointments = 0;

	/**
	 * Mostra les ciutats de les cites actives
	 */
	protected Collection<SelectItem> ciutatsList = new ArrayList<SelectItem>();

	/**
	 * Mostra les dates de les cites actives
	 */
	protected Collection<SelectItem> datesList = new ArrayList<SelectItem>();

	/**
	 * Mostra les hores de les cites actives
	 */
	protected Collection<SelectItem> horesList = new ArrayList<SelectItem>();

	/**
	 * Mostra els llenguatges de les cites actives
	 */
	protected Collection<SelectItem> languagesList = new ArrayList<SelectItem>();

	/**
	 * Mostra la ciutat activa al desplegable
	 */
	private String ciutat = "ALL CITIES";

	/**
	 * Mostra la data activa al desplegable
	 */
	private String dates = "ALL DATES";

	/**
	 * 	Mostra la hora activa del desplegable
	 */
	private String hores = "ALL HOURS";


	/**
	 * Nostra els llenguatga actiu del desplegable
	 */
	private String languages = "ALL LANGUAGES";

	//store all the instances of TalkAppointments
	private Collection<TalkAppointmentJPA> myTalkAppointmentsList;

	/*Aquesta variable contindra els 10 idiomes o menys que es mostren actualment per pantalla*/
	protected Collection<TalkAppointmentJPA> myTalkAppointmentsListView;

	/*
	 * Retornen els valors del ejb que es mostrarant al desplegable
	 */
	private Collection<String> citiesList;
	private Collection<Date> fechasList;
	private Collection<Time> timeList;
	private Collection<String> languageList;

	public FindMyTalkAppointmentsMBean() throws Exception 
	{			
		this.myTalkAppointmentsList();

		this.ciutatsList();
		this.datesList();
		this.horesList();
		this.languagesList();
	}

	public Collection<SelectItem> getCiutatsList() {
		return ciutatsList;
	}

	public Collection<SelectItem> getDatesList() {
		return datesList;
	}

	public Collection<SelectItem> getHoresList() {
		return horesList;
	}

	public Collection<SelectItem> getLanguagesList() {		
		return languagesList;
	}

	/**
	 * Get/Set the City name
	 * @return City name
	 */
	public String getCiutat() {
		return this.ciutat;
	}

	public void setCiutat(String ciutat) {
		this.ciutat = ciutat;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDates() {
		return this.dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getHores() {
		return this.hores;
	}

	public void setHores(String hores) {
		this.hores = hores;
	}

	public String getLanguages() {
		return this.languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public void ciutatValueChanged(ValueChangeEvent ciutatChanged) {
		this.setCiutat(ciutatChanged.getNewValue().toString());

		try {
			this.setLanguages("ALL LANGUAGES");
			this.setDates("ALL DATES");
			this.setHores("ALL HOURS");

			this.languagesList();		
			this.datesList();			
			this.horesList();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void languagesValueChanged(ValueChangeEvent languagesChanged) {		
		this.setLanguages(languagesChanged.getNewValue().toString());

		try {

			this.setDates("ALL DATES");
			this.setHores("ALL HOURS");

			this.datesList();
			this.horesList();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void datesValueChanged(ValueChangeEvent datesChanged) {				
		this.setDates(datesChanged.getNewValue().toString());

		try {

			this.setHores("ALL HOURS");

			this.horesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void horesValueChanged(ValueChangeEvent horesChanged) {
		this.setHores(horesChanged.getNewValue().toString());
	}

	public String listMyTalkAppointments() throws Exception {		
		
		myTalkAppointmentsList();
		
		/*Si hi ha unicament un registre a mostrar redirigim a la vista de showTalkAppointment, passant per parametre GET el id del talkAppointment*/
		if (myTalkAppointmentsList.size()==1)
		{
		    return "showTalkAppointmentView?faces-redirect=true&id="+myTalkAppointmentsList.iterator().next().getId();		
		}
			
		return "MyTalkAppointmentsListView";
	}

	public Collection<TalkAppointmentJPA> getMyTalkAppointmentsListView() throws Exception 
	{		
		int n=0;		
		myTalkAppointmentsListView = new ArrayList<TalkAppointmentJPA>();
		for (Iterator<TalkAppointmentJPA> iter2 = myTalkAppointmentsList.iterator(); iter2.hasNext();)
		{
			TalkAppointmentJPA aux = (TalkAppointmentJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.myTalkAppointmentsListView.add(aux);
			}
			n +=1;
		}

		// anteriorment nomes havia return myTalkAppointmentsList;
		this.numberTalkAppointments = n;
		return myTalkAppointmentsListView;
	}

	public void nextScreen()
	{
		if (((screen+1)*10 < myTalkAppointmentsList.size()))
		{
			screen +=1;
		}
	}
	public void previousScreen()
	{
		if ((screen > 0))
		{
			screen -=1;
		}
	}

	@SuppressWarnings("unchecked")
	public void ciutatsList() throws Exception
	{
		ciutatsList.clear();

		this.ciutatsList.add(new SelectItem("ALL CITIES"));	

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		findMyTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");		

		citiesList = (Collection<String>) findMyTalkAppointmentsRemote.citiesMyTalkAppointments(this.getNif());


		if ( !(citiesList == null))
		{					
			for(Iterator<String> iter2 = citiesList.iterator(); iter2.hasNext();) {
				String ciutat = (String) iter2.next();
				SelectItem item = new SelectItem(ciutat);
				this.ciutatsList.add(item);
			}

		}

	}

	@SuppressWarnings("unchecked")
	public void languagesList() throws Exception
	{
		languagesList.clear();

		this.languagesList.add(new SelectItem("ALL LANGUAGES"));

		if ( this.getCiutat().compareTo("ALL CITIES") != 0 ) {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			findMyTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");

			languageList = (Collection<String>) findMyTalkAppointmentsRemote.languagesfromMyTalkAppointments(this.getNif(), this.getCiutat());

			if ( !languageList.isEmpty()) {
				for(Iterator<String> iter2 = languageList.iterator(); iter2.hasNext();) {
					String language = (String) iter2.next();
					SelectItem item = new SelectItem(language);
					this.languagesList.add(item);
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void datesList() throws Exception 
	{		
		datesList.clear();
		this.datesList.add(new SelectItem("ALL DATES"));

		if ( this.getLanguages().compareTo("ALL LANGUAGES") != 0 ) {

			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			findMyTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");

			fechasList = (Collection<Date>) findMyTalkAppointmentsRemote.datefromMyTalkAppointmments(this.getNif(), this.getCiutat(),this.getLanguages());

			if ( !fechasList.isEmpty() ) {
				for(Iterator<Date> iter2 = fechasList.iterator(); iter2.hasNext();) {
					Date dates = (Date) iter2.next();
					SelectItem item = new SelectItem(dates);
					this.datesList.add(item);
				}
			} 
		}

	}

	@SuppressWarnings("unchecked")
	public void horesList() throws Exception
	{
		horesList.clear();
		this.horesList.add(new SelectItem("ALL HOURS"));

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

		if ( this.getDates().compareTo("ALL DATES") != 0 ) {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			findMyTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");

			Date fecha = (Date) formatDate.parse(this.getDates());		
			timeList = (Collection<Time>) findMyTalkAppointmentsRemote.timefromMyTalkAppointments(this.getNif(), this.getCiutat(),this.getLanguages(),fecha);

			if ( !timeList.isEmpty() ) {
				for(Iterator<Time> iter2 = timeList.iterator(); iter2.hasNext();) {
					Time time = (Time) iter2.next();
					SelectItem item = new SelectItem(time);
					this.horesList.add(item);
				}
			}			
		}

	}

	/*
	 * Metode que es crida en el boto "confirm" dins de la vista
	 * FindTalkAppointmentsView.
	 * S'haura de passar la data, hora , i el llenguatge
	 */
	@SuppressWarnings("unchecked")
	private void myTalkAppointmentsList() throws Exception {	
		this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);					
		screen = 0;
		findMyTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		myTalkAppointmentsList = (Collection<TalkAppointmentJPA>) findMyTalkAppointmentsRemote.findMyTalkAppointments(this.getNif(), this.getCiutat(),this.getDates(),this.getHores(),this.getLanguages());
	}

}
