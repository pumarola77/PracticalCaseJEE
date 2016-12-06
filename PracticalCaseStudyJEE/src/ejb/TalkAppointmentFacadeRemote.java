package ejb;

import java.util.Collection;

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
	public Collection<TalkAppointmentJPA> findMyTalkAppointments(String nif, boolean isPublisher);
}
