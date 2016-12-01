package ejb;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import jpa.TalkAppointmentJPA;

@Local
public interface TalkAppointmentFacade {
	/*
	 Metodes invocats localment
	 */
	public TalkAppointmentJPA showTalkAppointment(int id);
}
