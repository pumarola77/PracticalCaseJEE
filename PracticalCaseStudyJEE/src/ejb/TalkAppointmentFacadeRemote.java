package ejb;

import javax.ejb.Remote;

import jpa.TalkAppointmentJPA;

@Remote
public interface TalkAppointmentFacadeRemote {
	/*
	 Metodes invocats remotament
	 */
	public TalkAppointmentJPA showTalkAppointment(int id);
}
