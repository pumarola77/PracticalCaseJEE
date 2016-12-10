package managedbean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import ejb.TalkAppointmentAdminFacadeRemote;
import ejb.UserFacadeRemote;
import jpa.LanguageToTalkJPA;
import jpa.LocationJPA;


/**
 * Managed Bean AddTalkedLanguageMBean
 */
@ManagedBean(name = "talkappointmentadd")
@SessionScoped
public class AddTalkAppointmentMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@EJB
	private TalkAppointmentAdminFacadeRemote addTalkAppointmentRemote;
	
	@EJB
	private UserFacadeRemote userRemote;
	
	private String nif;
	LanguageToTalkJPA languageToTalk = null;
	
	//Necessari per llista totes les llengues que parla l'usuari
	protected String language = "";
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
	
	public LanguageToTalkJPA getLanguageToTalk(){
		return languageToTalk;
	}
	
	public void setLanguageToTalk(LanguageToTalkJPA languageToTalk){
		this.languageToTalk = languageToTalk;
	}
	
	public Collection<SelectItem> getLanguageList(){
		return languageList;
	}

	public String addTalkApp() throws Exception{
		try{

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
			//Description
			String description = request.getParameter("talkAppForm:description");
			//System.out.println("DESCRIPTION:" +description);
			//Date
			String dateStr = request.getParameter("talkAppForm:date");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			java.util.Date dateConv = dateFormat.parse(dateStr);
			Date date = new Date(dateConv.getTime());
			//System.out.println("DATE:" +date);
			//Time
			String timeStr = request.getParameter("talkAppForm:time");
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
			long timeConv = timeFormat.parse(timeStr).getTime();
			Time time = new Time(timeConv);
			//System.out.println("TIME:" +time);
			
			//LanguageToTalk
			findLanguageToTalk();
			//System.out.println("LANGUAGETOTALK:" +languageToTalk.getLanguage());
			
			//Location
			String street = request.getParameter("talkAppForm:street");
			String num = request.getParameter("talkAppForm:num");
			String cp = request.getParameter("talkAppForm:cp");
			String city = request.getParameter("talkAppForm:city");
			LocationJPA location = new LocationJPA(street,num,cp,city);
			//System.out.println("LOCATION:" +location.toString());
			
			//TalkAppointmentAdminFacadeRemote
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			addTalkAppointmentRemote = (TalkAppointmentAdminFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentAdminBean!ejb.TalkAppointmentAdminFacadeRemote");
			
			//Afegim el TalkAppointment
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
}
