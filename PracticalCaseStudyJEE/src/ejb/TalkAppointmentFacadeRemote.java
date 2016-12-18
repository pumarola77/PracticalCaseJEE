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
	public TalkAppointmentJPA showTalkAppointment(int id);
	public void registerInTalkAppointment(String nif, Integer talkid);
	public void removeFromTalkAppointment(String nif, Integer talkid);
	public Collection<TalkAppointmentJPA> findMyTalkAppointments(String nif, String data, String tipus);

	public Collection<?> citiesTalkAppointments();
	public Collection<?> languagesfromTalkAppointments(String city);
	public Collection<?> datefromTalkAppointmments(String city, String language);
	public Collection<?> timefromTalkAppointments(String city, String language, Date date);
	public Collection<?> findTalkAppointments(String location,String fecha,String hora,String language);

}
