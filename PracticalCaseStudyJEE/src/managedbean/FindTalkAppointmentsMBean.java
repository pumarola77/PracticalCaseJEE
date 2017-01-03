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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.TalkAppointmentFacadeRemote;
import jpa.TalkAppointmentJPA;

/**
 * ManagedBean findtalkappointments
 * 
 * @author Grup 6
 * @version 1.0
 *
 */
@ManagedBean(name = "findtalkappointments")
@ViewScoped
public class FindTalkAppointmentsMBean implements Serializable {

	/**
	 * Obligatori perque la classe implementa serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * EJB TalkAppointmentFacadeRemote
	 */
	@EJB
	private TalkAppointmentFacadeRemote findTalkAppointmentsRemote;

	/**
	 * Controla el numero de registres que es visualitzen per pantalla
	 */
	private int screen = 0;
	
	/**
	 * Numero total de cites
	 */
	protected int numberTalkAppointments = 0;
	
	/**
	 * Identificador usuari
	 */
	private String nif;

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

	/**
	 * store all the instances of TalkAppointments
	 */
	private Collection<TalkAppointmentJPA> talkAppointmentsList;

	/**
	 * Aquesta variable contindra els 10 idiomes o menys que es mostren actualment per pantalla
	 */
	protected Collection<TalkAppointmentJPA> talkAppointmentsListView;

	/**
	 * llista de ciutats
	 */
	private Collection<String> citiesList;

	/**
	 * Llista de dates
	 */
	private Collection<Date> fechasList;
	
	/**
	 * Llista de hores
	 */
	private Collection<Time> timeList;
	
	/**
	 * Llista de llenguatges
	 */
	private Collection<String> languageList;

	/**
	 * Constructor
	 * @throws Exception indicencia al constructor
	 */
	public FindTalkAppointmentsMBean() throws Exception 
	{			
		this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());

		this.talkAppointmentsList();

		this.ciutatsList();
		this.datesList();
		this.horesList();
		this.languagesList();
	}

	/**
	 * Getter llista ciutats
	 * @return llista ciutats
	 */
	public Collection<SelectItem> getCiutatsList() {
		return ciutatsList;
	}

	/**
	 * Getter llista de dates
	 * @return llista de dates
	 */
	public Collection<SelectItem> getDatesList() {
		return datesList;
	}

	/**
	 * Getter Llista hores
	 * @return llista hores
	 */
	public Collection<SelectItem> getHoresList() {
		return horesList;
	}

	/**
	 * llista de llenguatges
	 * @return llista de llenguatges
	 */
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

	/**
	 * Setter ciutat
	 * @param ciutat ciutat
	 */
	public void setCiutat(String ciutat) {
		this.ciutat = ciutat;
	}

	/**
	 * Getter dates
	 * @return dates 
	 */
	public String getDates() {
		return this.dates;
	}

	/**
	 * Setter dates
	 * @param dates dates
	 */
	public void setDates(String dates) {
		this.dates = dates;
	}

	/**
	 * Getter hores
	 * @return hores
	 */
	public String getHores() {
		return this.hores;
	}

	/**
	 * Setter hores
	 * @param hores hores
	 */
	public void setHores(String hores) {
		this.hores = hores;
	}

	/**
	 * Getter languages
	 * @return languages
	 */
	public String getLanguages() {
		return this.languages;
	}

	/**
	 * Setter languages
	 * @param languages idioma
	 */
	public void setLanguages(String languages) {
		this.languages = languages;
	}

	/**
	 * Getter nif
	 * @return nif
	 */
	public String getNif() {
		return this.nif;
	}

	/**
	 * Setter nif
	 * @param nif nif
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * Al triar una ciutat es carrega el desplegable de idiomes, dates i hores
	 * @param ciutatChanged ciutat
	 */
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
			e.printStackTrace();
		}

	}

	/**
	 * Al triar un idioma es carrega el desplegable de dates i hores
	 * @param languagesChanged idioma
	 */
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

	/**
	 * Al triar una data es carrega el desplegable de hores
	 * @param datesChanged data
	 */
	public void datesValueChanged(ValueChangeEvent datesChanged) {				
		this.setDates(datesChanged.getNewValue().toString());

		try {

			this.setHores("ALL HOURS");

			this.horesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Assigna les hores del desplegable a la variable
	 * @param horesChanged hora
	 */
	public void horesValueChanged(ValueChangeEvent horesChanged) {
		this.setHores(horesChanged.getNewValue().toString());
	}

	/**
	 * Fa la cerca dels TalkAppointments a mostrar
	 * @return Nom de la vista a carregar
	 * @throws Exception llista cites
	 */
	public String listTalkAppointments() throws Exception {				
		talkAppointmentsList();

		/*Si hi ha unicament un registre a mostrar redirigim a la vista de showTalkAppointment, passant per parametre GET el id del talkAppointment*/
		if (talkAppointmentsList.size()==1)
		{
			return "showTalkAppointmentView?faces-redirect=true&id=" + talkAppointmentsList.iterator().next().getId() + "&returnView=TalkAppointmentsListView";
		}
		return "TalkAppointmentsListView";
	}

	/**
	 * Obte els 10 registres seguents de la llista
	 * @return llista de cites
	 * @throws Exception obte llista de cites
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
	 * nextScreen
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < talkAppointmentsList.size()))
		{
			screen +=1;
		}
	}
	
	/**
	 * PreviousScreen
	 */
	public void previousScreen()
	{
		if ((screen > 0))
		{
			screen -=1;
		}
	}

	/**
	 * Despegable ciutats
	 * @throws Exception incidencia al carregar desplegable ciutats
	 */
	@SuppressWarnings("unchecked")
	public void ciutatsList() throws Exception
	{
		ciutatsList.clear();

		this.ciutatsList.add(new SelectItem("ALL CITIES"));	

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		findTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");		

		citiesList = (Collection<String>) findTalkAppointmentsRemote.citiesTalkAppointments(this.getNif());


		if ( !(citiesList == null))
		{					
			for(Iterator<String> iter2 = citiesList.iterator(); iter2.hasNext();) {
				String ciutat = (String) iter2.next();
				SelectItem item = new SelectItem(ciutat);
				this.ciutatsList.add(item);
			}

		}

	}

	/**
	 * Desplegable llenguatges
	 * @throws Exception incidencia al carregar desplegable idioma
	 */
	@SuppressWarnings("unchecked")
	public void languagesList() throws Exception
	{
		languagesList.clear();

		this.languagesList.add(new SelectItem("ALL LANGUAGES"));

		if ( this.getCiutat().compareTo("ALL CITIES") != 0 ) {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			findTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");

			languageList = (Collection<String>) findTalkAppointmentsRemote.languagesfromTalkAppointments(this.getNif(),this.getCiutat());

			if ( !languageList.isEmpty()) {
				for(Iterator<String> iter2 = languageList.iterator(); iter2.hasNext();) {
					String language = (String) iter2.next();
					SelectItem item = new SelectItem(language);
					this.languagesList.add(item);
				}
			}
		}

	}

	/**
	 * Desplegable dates
	 * @throws Exception incidencia al carregar desplegable data
	 */
	@SuppressWarnings("unchecked")
	public void datesList() throws Exception 
	{		
		datesList.clear();
		this.datesList.add(new SelectItem("ALL DATES"));

		if ( this.getLanguages().compareTo("ALL LANGUAGES") != 0 ) {

			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			findTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");

			fechasList = (Collection<Date>) findTalkAppointmentsRemote.datefromTalkAppointmments(this.getNif(),this.getCiutat(),this.getLanguages());

			if ( !fechasList.isEmpty() ) {
				for(Iterator<Date> iter2 = fechasList.iterator(); iter2.hasNext();) {
					Date dates = (Date) iter2.next();
					SelectItem item = new SelectItem(dates);
					this.datesList.add(item);
				}
			} 
		}

	}

	/**
	 * Desplegable hores
	 * @throws Exception incidencia al carregar desplegable hores
	 */
	@SuppressWarnings("unchecked")
	public void horesList() throws Exception
	{
		horesList.clear();
		this.horesList.add(new SelectItem("ALL HOURS"));

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

		if ( this.getDates().compareTo("ALL DATES") != 0 ) {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			findTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");

			Date fecha = (Date) formatDate.parse(this.getDates());		
			timeList = (Collection<Time>) findTalkAppointmentsRemote.timefromTalkAppointments(this.getNif(),this.getCiutat(),this.getLanguages(),fecha);

			if ( !timeList.isEmpty() ) {
				for(Iterator<Time> iter2 = timeList.iterator(); iter2.hasNext();) {
					Time time = (Time) iter2.next();
					SelectItem item = new SelectItem(time);
					this.horesList.add(item);
				}
			}			
		}

	}

	/**
	 * Metode que es crida en el boto "confirm" dins de la vista
	 * FindTalkAppointmentsView.
	 * S'haura de passar la data, hora , i el llenguatge
	 */
	@SuppressWarnings("unchecked")
	private void talkAppointmentsList() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);	

		screen = 0;
		findTalkAppointmentsRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		talkAppointmentsList = (Collection<TalkAppointmentJPA>) findTalkAppointmentsRemote.findTalkAppointments(this.getNif(),this.getCiutat(),this.getDates(),this.getHores(),this.getLanguages());
	}




}
