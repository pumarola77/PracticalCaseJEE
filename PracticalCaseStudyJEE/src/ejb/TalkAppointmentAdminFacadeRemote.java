package ejb;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

import javax.ejb.Remote;
import javax.persistence.PersistenceException;

import jpa.LanguageToTalkJPA;
import jpa.LocationJPA;

/**
 * 
 * Acces EJB metodes remots
 * 
 * @author Grup 6
 * @version 1.0
 *
 */

@Remote
public interface TalkAppointmentAdminFacadeRemote {

	/**
	 * Donar alta una cita
	 * 
	 * @param description descripcio
	 * @param location adreça
	 * @param date data
	 * @param time hora
	 * @param languageToTalk llenguatge
	 */
	public void addTalkAppointment(String description, LocationJPA location, Date date, Time time, LanguageToTalkJPA languageToTalk);
	
	/**
	 * Accepta la peticio d'una cita
	 * @param talkid identificador cita
	 * @param nif identificador usuari
	 */
	public void acceptRequest(Long talkid, String nif);
	//public int rejectRequest(Long talkid, String nif, String reason);
	
	/**
	 * Rebutja la petició d'una cita
	 * 
	 * @param talkid identificador cita
	 * @param nif identificador usuari
	 * @param reason motiu 
	 */
	public void rejectRequest(Long talkid, String nif, String reason);
	
	/**
	 * Llista de les cites publicades per un usuari
	 * 
	 * @param nif identificador usuari
	 * @return llista amb les cites
	 */
	public Collection<?> findMyTalkAppointmentsAsProposal(String nif);
}
