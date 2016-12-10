package ejb;

import java.sql.Date;
import java.sql.Time;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import jpa.LanguageToTalkJPA;
import jpa.LocationJPA;
import jpa.TalkAppointmentJPA;
import jpa.TalkStatus;
import jpa.TalkedLanguageJPA;
import jpa.UserJPA;

//@Stateless: Indiquem que es tracta de un EJB Session sense estat.
@Stateless
public class TalkAppointmentAdminBean implements TalkAppointmentAdminFacadeRemote, TalkAppointmentAdminFacade {

	//@PersistenceContext: Indiquem quin context per la persistencia utilitzara el EntityManager
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;
	
	@EJB
	private UserFacade userFacade;
		
	public void addTalkAppointment(String description, LocationJPA location, Date date, Time time, LanguageToTalkJPA languageToTalk){
		
		try{
			LocationJPA locat = new LocationJPA(location.getStreet(),location.getNum(),location.getCp(),location.getCity());
			entman.persist(locat);
			TalkAppointmentJPA talkApp = new TalkAppointmentJPA(description,date,time,TalkStatus.OPEN);
			talkApp.setLocation(locat);
			talkApp.setUserPublish(languageToTalk.getUser());
			talkApp.setUserLanguageToTalk(languageToTalk.getLanguage());
			talkApp.setLanguageToTalk(languageToTalk); // No esta guardant res? El id es (language,nif)...
			entman.persist(talkApp);
			
		} catch(PersistenceException e){
			throw e;
		}
	}
}
