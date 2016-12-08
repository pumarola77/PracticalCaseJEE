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
			TalkAppointmentJPA talkApp = new TalkAppointmentJPA(description,date,time,TalkStatus.OPEN);
			talkApp.setUserPublish(languageToTalk.getUser());
			talkApp.setLocation(location);
			talkApp.setUserLanguageToTalk(languageToTalk.getUser().getNif());
			talkApp.setLanguageToTalk(languageToTalk);
			entman.persist(talkApp);
		} catch(PersistenceException e){
			throw e;
		}
	}
}
