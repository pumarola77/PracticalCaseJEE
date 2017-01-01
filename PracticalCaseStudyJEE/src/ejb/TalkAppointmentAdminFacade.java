package ejb;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import javax.ejb.Local;
import jpa.LanguageToTalkJPA;
import jpa.LocationJPA;

/**
 * 
 * EJB Metodes Locals
 * 
 * @author Grup 6
 * @version 1.0
 *
 */

@Local
public interface TalkAppointmentAdminFacade {
	
	/**
	 * 
	 * Dona Alta una cita
	 * 
	 * @param description descripcio 
	 * @param location adreça
	 * @param date data
	 * @param time hora
	 * @param languageToTalk llenguatge
	 */
	public void addTalkAppointment(String description, LocationJPA location, Date date, Time time, LanguageToTalkJPA languageToTalk);
	
	/**
	 * 
	 * @param talkid identificador cita
	 * @param nif identificador del usuari
	 */
	public void acceptRequest(Long talkid, String nif);
	//public int rejectRequest(Long talkid, String nif, String reason);
	
	/**
	 * 
	 * @param talkid identificador cita
	 * @param nif identificador del usuari
	 * @param reason motiu per rebutjar una cita
	 */
	public void rejectRequest(Long talkid, String nif, String reason);
	
	/**
	 * 
	 * @param nif identificador usuari
	 * @return les cites d'un usuari
	 */
	public Collection<?> findMyTalkAppointmentsAsProposal(String nif);
}
