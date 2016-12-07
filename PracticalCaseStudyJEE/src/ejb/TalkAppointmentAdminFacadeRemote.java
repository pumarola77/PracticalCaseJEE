package ejb;

import java.sql.Date;
import java.sql.Time;

import javax.ejb.Remote;
import javax.persistence.PersistenceException;

import jpa.LanguageToTalkJPA;
import jpa.LocationJPA;

@Remote
public interface TalkAppointmentAdminFacadeRemote {
	/*
	 Metodes invocats remotament
	 */
	public void AddTalkAppointment(String description, LocationJPA location, Date date, Time time, LanguageToTalkJPA languageToTalk);
}
