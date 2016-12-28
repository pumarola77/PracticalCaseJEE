package ejb;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Remote;

import jpa.TalkAppointmentJPA;

@Remote
public interface TalkAppointmentFacadeRemote {
	/*
	 Metodes invocats remotament
	 */
	public TalkAppointmentJPA showTalkAppointment(Long id);
	public void registerInTalkAppointment(String nif, Long talkid);
	public void removeFromTalkAppointment(String nif, Long talkid);
	//public Collection<TalkAppointmentJPA> findMyTalkAppointments(String nif, String data, String tipus);

	public Collection<?> citiesTalkAppointments(String nif);
	public Collection<?> languagesfromTalkAppointments(String nif, String city);
	public Collection<?> datefromTalkAppointmments(String nif, String city, String language);
	public Collection<?> timefromTalkAppointments(String nif, String city, String language, Date date);
	public Collection<?> findTalkAppointments(String nif, String location,String fecha,String hora,String language);
	
	public Collection<?> citiesMyTalkAppointments(String nif);
	public Collection<?> languagesfromMyTalkAppointments(String nif, String city);
	public Collection<?> datefromMyTalkAppointmments(String nif, String city, String language);
	public Collection<?> timefromMyTalkAppointments(String nif, String city, String language, Date date);
	public Collection<?> findMyTalkAppointments(String nif, String location,String fecha,String hora,String language);

}
