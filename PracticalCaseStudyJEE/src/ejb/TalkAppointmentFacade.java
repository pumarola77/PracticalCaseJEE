package ejb;

import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import jpa.TalkAppointmentJPA;

@Local
public interface TalkAppointmentFacade {
	/*
	 Metodes invocats localment
	 */
	public TalkAppointmentJPA showTalkAppointment(Long id);
	public void registerInTalkAppointment(String nif, Long talkid);
	public void removeFromTalkAppointment(String nif, Long talkid);
	//public Collection<TalkAppointmentJPA> findMyTalkAppointments(String nif, String data, String tipus);

	public Collection<?> citiesTalkAppointments();
	public Collection<?> languagesfromTalkAppointments(String city);
	public Collection<?> datefromTalkAppointmments(String city, String language);
	public Collection<?> timefromTalkAppointments(String city, String language, Date date);
	public Collection<?> findTalkAppointments(String location,String fecha,String hora,String language);
	
	public Collection<?> citiesMyTalkAppointments(String nif);
	public Collection<?> languagesfromMyTalkAppointments(String nif, String city);
	public Collection<?> datefromMyTalkAppointmments(String nif, String city, String language);
	public Collection<?> timefromMyTalkAppointments(String nif, String city, String language, Date date);
	public Collection<?> findMyTalkAppointments(String nif, String location,String fecha,String hora,String language);
}
