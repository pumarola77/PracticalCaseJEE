package ejb;

import java.util.Collection;

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
}
