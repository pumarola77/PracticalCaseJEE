package ejb;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import jpa.TalkAppointmentJPA;

@Local
public interface TalkAppointmentFacade {
	/*
	 Metodes invocats localment
	 */
	public TalkAppointmentJPA showTalkAppointment(int id);
	public void registerInTalkAppointment(String nif, Integer talkid);
	public void removeFromTalkAppointment(String nif, Integer talkid);
	public Collection<TalkAppointmentJPA> findMyTalkAppointments(String nif, boolean isPublisher);
	
	public Collection<?> citiesTalkAppointments();
	public Collection<?> languagesfromTalkAppointments(String city);
	public Collection<?> datefromTalkAppointmments(String city, String language);
	public Collection<?> timefromTalkAppointments(String city, String language, Date date);
	public Collection<?> findTalkAppointments(String location,String fecha,String hora,String language);
}
