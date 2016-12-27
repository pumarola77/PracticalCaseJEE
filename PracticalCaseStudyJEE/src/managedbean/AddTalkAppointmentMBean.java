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
 * Managed Bean AddTalkedLanguageMBean
 */
@ManagedBean(name = "talkappointmentadd")
@ViewScoped
public class AddTalkAppointmentMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	
	@EJB
	private TalkAppointmentAdminFacadeRemote addTalkAppointmentRemote;
	
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
	
	protected String description = "";
	protected String dateStr = "";
	protected String timeStr = "";
	//protected String language = "";
	protected String street = "";
	protected String num = "";
	protected String cp = "";
	protected String city = "";
	
	
	private Collection<LanguageToTalkJPA> allLanguageToTalk;
	protected Collection<SelectItem> languageList = new ArrayList<SelectItem>();
	
	//Constructor
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
	public String getNif(){
		return nif;
	}
	
	public void setNif(String nif){
		this.nif = nif;
	}
	
	public String getLanguage(){
		return language;
	}
	
	public void setLanguage(String language){
		this.language = language;
	}
	

	public String getStreet(){
		return street;
	}
	
	public void setStreet(String street){
		this.street = street;
	}
	
	public String getNum(){
		return num;
	}
	
	public void setNum(String num){
		this.num = num;
	}
	
	public String getCp(){
		return cp;
	}
	
	public void setCp(String cp){
		this.cp = cp;
	}
	
	public String getCity(){
		return city;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDateStr(){
		return dateStr;
	}
	
	public void setDateStr(String dateStr){
		this.dateStr = dateStr;
	}
	
	public String getTimeStr(){
		return timeStr;
	}
	
	public void setTimeStr(String timeStr){
		this.timeStr = timeStr;
	}
	
	
	public Collection<SelectItem> getLanguageList(){
		return languageList;
	}
	

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
	
	//Carregar la llista de totes les llengues de l'usuari
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

	//Recuperar valor de XHTML
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
