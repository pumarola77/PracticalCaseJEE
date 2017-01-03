package managedbean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import ejb.TalkAppointmentAdminFacadeRemote;
import ejb.UserFacadeRemote;
import jpa.LanguageToTalkJPA;
import jpa.LocationJPA;
import jpa.UserJPA;


/**
 * Managed Bean AddTalkedLanguagedMBean
 * @author Bazinga
 * @version 1.0
 */
@ManagedBean(name = "talkappointmentadd")
@ViewScoped
public class AddTalkAppointmentMBean implements Serializable{
	
	/**
	 * Obligatori perque la classe es serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Persistencia 
	 */
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	/**
	 * EJB TalkAppointmentAdminFacadeRemote
	 */
	@EJB
	private TalkAppointmentAdminFacadeRemote addTalkAppointmentRemote;
	
	/**
	 * EJB userRemote
	 */
	@EJB
	private UserFacadeRemote userRemote;
	
	/**
	 * Nif usuari que s'ha logat
	 */
	private String nif;
	
	/**
	 * Llengua seleccionada per el 
	 */
	private String language;
	
	/**
	 * Variable description
	 */
	protected String description = "";
	
	/**
	 * Variable date
	 */
	protected String dateStr = "";
	
	/**
	 * Variable TimeStr
	 */
	protected String timeStr = "";
	
	/**
	 * Variable street
	 */
	protected String street = "";
	
	/**
	 * Variable num
	 */
	protected String num = "";
	
	/**
	 * Variable cp
	 */
	protected String cp = "";
	
	/**
	 * Variable city
	 */
	protected String city = "";
	
	/**
	 * Llista dels llenguatges Parlats
	 */
	private Collection<LanguageToTalkJPA> allLanguageToTalk;
	
	/**
	 * Desplegable dels llenguatges
	 */
	protected Collection<SelectItem> languageList = new ArrayList<SelectItem>();
	
	/**
	 * Constructor classe
	 * @throws Exception Excepcio al constructor
	 */
	public AddTalkAppointmentMBean() throws Exception{
		//User Remote
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		
		//Carrega el NIF del User
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == true) {
			this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());
		}
		//Carrega les language que vol parlar User
		//this.getAllLanguageToTalk();
	}
	
	// Getters / Setters
	
	/**
	 * Getter nif
	 * @return nif
	 */
	public String getNif(){
		return nif;
	}
	
	/**
	 * Setter nif
	 * @param nif identificador usuari
	 */
	public void setNif(String nif){
		this.nif = nif;
	}
	
	/**
	 * Getter language
	 * @return language 
	 */
	public String getLanguage(){
		return language;
	}
	
	/**
	 * Setter language
	 * @param language llenguatge
	 */
	public void setLanguage(String language){
		this.language = language;
	}
	

	/**
	 * Getter street
	 * @return street
	 */
	public String getStreet(){
		return street;
	}
	
	/**
	 * Setter street
	 * @param street carrer
	 */
	public void setStreet(String street){
		this.street = street;
	}
	
	/**
	 * Getter num
	 * @return num
	 */
	public String getNum(){
		return num;
	}
	
	/**
	 * Setter num
	 * @param num numero
	 */
	public void setNum(String num){
		this.num = num;
	}
	
	/**
	 * Getter cp
	 * @return cp
	 */
	public String getCp(){
		return cp;
	}
	
	/**
	 * Setter cp
	 * @param cp codi postal
	 */
	public void setCp(String cp){
		this.cp = cp;
	}
	
	/**
	 * Getter city
	 * @return city
	 */
	public String getCity(){
		return city;
	}
	
	/**
	 * Setter city
	 * @param city ciutat
	 */
	public void setCity(String city){
		this.city = city;
	}
	
	/**
	 * Getter description
	 * @return description
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Setter description
	 * @param description descripcio
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Getter DateStr
	 * @return date
	 */
	public String getDateStr(){
		return dateStr;
	}
	
	/**
	 * Setter date
	 * @param dateStr date
	 */
	public void setDateStr(String dateStr){
		this.dateStr = dateStr;
	}
	
	/**
	 * Getter Time
	 * @return time
	 */
	public String getTimeStr(){
		return timeStr;
	}
	
	/**
	 * Setter time
	 * @param timeStr time
	 */
	public void setTimeStr(String timeStr){
		this.timeStr = timeStr;
	}
	
	
	/**
	 * Getter Language list
	 * @return language list
	 */
	public Collection<SelectItem> getLanguageList(){
		return languageList;
	}
	

	/**
	 * Alta llenguatge
	 * @return vista principal
	 * @throws Exception al afegir llenguatge
	 */
	public String addTalkApp() throws Exception{
		try{

			
			//Date
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dateConv = dateFormat.parse(dateStr);
			Date date = new Date(dateConv.getTime());
			
			//Time
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
			long timeConv = timeFormat.parse(timeStr).getTime();
			Time time = new Time(timeConv);
			
			
			//LanguageToTalk
			//findLanguageToTalk();
			
			LanguageToTalkJPA findlanguageToTalk;
								
			//Location
			LocationJPA location = new LocationJPA(street,num,cp,city);
			
			// UTILITZA per buscar la clau composta corresponent al nif i llenguatge entrat.
			UserJPA user = entman.find(UserJPA.class, this.getNif());
			LanguageToTalkJPA pk = new LanguageToTalkJPA(user,this.getLanguage());
			findlanguageToTalk = entman.find(LanguageToTalkJPA.class, pk);
			LanguageToTalkJPA languageToTalk = new LanguageToTalkJPA(findlanguageToTalk.getLanguage(),findlanguageToTalk.getLevel(),
					findlanguageToTalk.getDescription(),findlanguageToTalk.isAcceptPay());
			
			//Assigna valors al constructor
			languageToTalk.setUser(user);
			languageToTalk.setLanguage(this.getLanguage());
			
			//TalkAppointmentAdminFacadeRemote
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			addTalkAppointmentRemote = (TalkAppointmentAdminFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentAdminBean!ejb.TalkAppointmentAdminFacadeRemote");
			addTalkAppointmentRemote.addTalkAppointment(description,location,date,time,languageToTalk);
	
		}catch(PersistenceException e){
			throw e;
		}
		return "HomeView";
	}
	
	/**
	 * Carrega la llista de tots els llenguatges de l'usuari
	 * @throws Exception obtenir llista de llenguatges
	 */
	public void getAllLanguageToTalk() throws Exception{
		//netejem la llista
		getLanguageList().clear();
		
		allLanguageToTalk = userRemote.listAllLanguagesToTalk(nif);
		Iterator<LanguageToTalkJPA> iter = allLanguageToTalk.iterator();
		
		while(iter.hasNext()){
			SelectItem item = new SelectItem(iter.next().getLanguage());
			getLanguageList().add(item);
		}
	}

	/**
	 * Desplegable llenguatge
	 * @param languageChanged idioma
	 * @throws Exception assignar valor del desplegable
	 */
	public void languageValueChanged(ValueChangeEvent languageChanged) throws Exception {
		setLanguage(languageChanged.getNewValue().toString());
	}	

	/*
	//Busquem el languageToTalk de la language seleccionada en la View
	private void findLanguageToTalk() throws Exception {
		
		Collection<LanguageToTalkJPA> languageToTalkFullList;
		languageToTalkFullList = userRemote.listAllLanguagesToTalk(getNif());
		Iterator<LanguageToTalkJPA> iter = languageToTalkFullList.iterator();
			
		while(iter.hasNext()){
			LanguageToTalkJPA aux = iter.next();
			if(aux.getLanguage().compareTo(getLanguage()) == 0){
					setLanguageToTalk(aux);
			}
		}
	}
	*/
}
