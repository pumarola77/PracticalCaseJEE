package ejb;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Remote;

import jpa.TalkAppointmentJPA;

/**
 * 
 * Metodes Remots Appointment
 * 
 * @author Grup 6
 * @version 1.0
 */
@Remote
public interface TalkAppointmentFacadeRemote {

	/**
	 * Mostra el detall d'una cita
	 * @param id identificador cita
	 * @return dades cita
	 */
	public TalkAppointmentJPA showTalkAppointment(Long id);
	
	/**
	 * Registra una cita
	 * @param nif identificador usuari
	 * @param talkid  identificador cita
	 * @return boolean realitzat correctament el regitre
	 */
	public Boolean registerInTalkAppointment(String nif, Long talkid);
	
	/**
	 * Esborra una cita ja registrada
	 * 
	 * @param nif identificador usuari
	 * @param talkid identificador cita
	 */
	public void removeFromTalkAppointment(String nif, Long talkid);

	/**
	 * Llista de ciutats disponibles per el desplegable
	 * 
	 * @param nif identificador usuari
	 * @return llista de ciutats
	 */
	public Collection<?> citiesTalkAppointments(String nif);
	
	/**
	 * Llista de llenguatges disponibles per el desplegable
	 * 
	 * @param nif identificador usuari
	 * @param city ciutat
	 * @return llista de llenguatges
	 */
	public Collection<?> languagesfromTalkAppointments(String nif, String city);
	
	/**
	 * Llista de dates disponibles per el desplegable
	 *  
	 * @param nif identificador usuari
	 * @param city ciutat
	 * @param language llenguatge
	 * @return llista de dates
	 */
	public Collection<?> datefromTalkAppointmments(String nif, String city, String language);
	
	/**
	 * Llista de hores disponibles per el desplegable 
	 * @param nif identificador usuari
	 * @param city ciutat
	 * @param language llenguatge
	 * @param date data
	 * @return llista de hores 
	 */
	public Collection<?> timefromTalkAppointments(String nif, String city, String language, Date date);
	
	/**
	 * Busca les cites disponibles
	 *  
	 * @param nif identificador usuari
	 * @param location adreça
	 * @param fecha data
	 * @param hora hora 
	 * @param language llenguatge
	 * @return llista de cites disponibles
	 */
	public Collection<?> findTalkAppointments(String nif, String location,String fecha,String hora,String language);
	
	/**
	 * Ciutats disponibles de les cites publicades per un usuari
	 * 
	 * @param nif identificador usuari
	 * @return llistat de ciutats disponibles
	 */
	public Collection<?> citiesMyTalkAppointments(String nif);
	
	/**
	 * Llenguatges disponibles de les cites publicades per un usuari
	 * 
	 * @param nif identificador usuari
	 * @param city ciutat
	 * @return llista de llenguatges
	 */
	public Collection<?> languagesfromMyTalkAppointments(String nif, String city);
	
	/**
	 * Dates disponibles de les cites publicades per un usuari
	 * 
	 * @param nif identificador usuari
	 * @param city ciutat
	 * @param language llenguatge
	 * @return llista de dates disponibles
	 */
	public Collection<?> datefromMyTalkAppointmments(String nif, String city, String language);
	
	/**
	 * Hores disponibles de les cites publicades per un usuari
	 * 
	 * @param nif identificador usuari
	 * @param city ciutat
	 * @param language llenguatge
	 * @param date dates
	 * @return llista de hores disponibles
	 */
	public Collection<?> timefromMyTalkAppointments(String nif, String city, String language, Date date);
	
	/**
	 * Llista de cites publicades per un usuari
	 * 
	 * @param nif identificador usuari
	 * @param location adreça 
	 * @param fecha data
	 * @param hora hora
	 * @param language llenguatge
	 * @return llista de cites publicades per un usuari
	 */
	public Collection<?> findMyTalkAppointments(String nif, String location,String fecha,String hora,String language);

}
