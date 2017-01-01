package ejb;

import java.util.Collection;
import javax.ejb.Remote;
import jpa.LanguageToTalkJPA;
import jpa.TalkedLanguageJPA;

import jpa.UserJPA;

/**
 * EJB Remot usuari
 * 
 * @author Grup 6
 * @version 1.0
 *
 */
@Remote
public interface UserFacadeRemote{

	/**
	 * 
	 * Registra un usuari
	 * 
	 * @param nif identificador usuari
	 * @param name nom
	 * @param surname cognom
 	 * @param phone telefon
	 * @param password password
	 * @param email email
	 * @return proces realitzat correctament
	 */
	public int registerUser(String nif, String name , String surname , String phone, String password , String email);
	
	/**
	 * Actualitzacio dades usuari
	 *  
	 * @param nif identificador usuari
	 * @param name nom
	 * @param surname cognom
	 * @param phone telefon
	 * @param password passowrd
	 * @param email email
	 * @return proces realitzat correctament
	 */
	public boolean updatePersonalData(String nif, String name , String surname , String phone, String password , String email);
	
	/**
	 * Troba un usuari
	 * 
	 * @param nif identificador usuari
	 * @return usuari
	 */
	public UserJPA findUser(String nif);
	
	/**
	 * Identificador usuari
	 * 
	 * @param email email
	 * @param password password
	 * @return si esta identificat
	 */
	public String login(String email, String pwd);
	
	/**
	 * Sortida aplicacio
	 * 
	 * @return sortida aplicacio
	 */
	public boolean logout();
	
	/**
	 * llista de llenguatges parlats per un usuari
	 * 
	 * @param nif identificador usuari
	 * @return llista de llenguatges
	 */
	public Collection<TalkedLanguageJPA> listAllTalkedLanguages(String nif);
	
	/**
	 * Dona alta un llenguatge parlat
	 * 
	 * @param nif identificador usuari
	 * @param language llenguatge
	 * @param level nivell
	 * @param description descripcio
	 * @return si alta s'ha realitzat correctaent
	 */
	public int addTalkedLanguage(String nif, String language, String level, String description);
	
	/**
	 * Esborra un llenguatge parlat
	 * 
	 * @param nif identificador usuari
	 * @param language llenguatge
	 */
	public void deleteTalkedLanguage(String nif, String language);
	
	/**
	 * Afegeix un llenguatge que vol parlar
	 * 
	 * @param nif identificador usuari
	 * @param language llenguatge
	 * @param level nivell
	 * @param description descripcio
	 * @param acceptPay accepta pagar
	 * @return
	 */
	public int addLanguageToTalk(String nif, String language, String level, String description, boolean acceptPay);
	
	
	/**
	 * Esborra llenguatge que vol parlar
	 * 
	 * @param nif identificador usuari
	 * @param language llenguatge
	 */
	public void deleteLanguageToTalk(String nif, String language);
	
	/**
	 * Llista de llenguatges que vol parlar un usuari
	 * 
	 * @param nif identificador usuari
	 * @return llista de llenguatges que vol parlar un usuari
	 */
	public Collection<LanguageToTalkJPA> listAllLanguagesToTalk(String nif);
	
	/**
	 * Verifica que un llenguatge existeixi en una cita.
	 * 
	 * @param nif identificador usuari
	 * @param language llenguatge
	 * @return si el llenguatge existeix
	 */
	public boolean findLanguageToTalkAppointment(String nif, String language);
}