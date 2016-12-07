package ejb;

import java.sql.Date;
import java.sql.Time;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import jpa.LanguageToTalkJPA;
import jpa.LocationJPA;

@Local
public interface TalkAppointmentAdminFacade {
	/*
	 Metodes invocats localment
	 */
	public void AddTalkAppointment(String description, LocationJPA location, Date date, Time time, LanguageToTalkJPA languageToTalk);
}