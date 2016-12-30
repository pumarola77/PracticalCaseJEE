package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import ejb.UserFacadeRemote;
import ejb.UserFacade;
import jpa.LanguageToTalkJPA;
import jpa.TalkedLanguageJPA;
import jpa.UserJPA;

//@Stateless: Indiquem que es tracta de un EJB Session sense estat.
@Stateless
public class UserFacadeBean implements UserFacadeRemote, UserFacade{

	//@PersistenceContext: Indiquem quin context per la persistencia utilitzara el EntityManager
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;


	/*
	 *Retorn: 
	 *	0 - Usuari creat correctament
	 *  1 - Error: Ja existeix un usuari amb el DNI indicat
	 *  2 - Error: Ja existeix un usuari amb el email indicat
	 */
	@Override
	public int registerUser(String nif, String name, String surname, String phone, String password, String email) 
	{			
		Query queryEmail = entman.createQuery("FROM UserJPA b WHERE b.email = :email").setParameter("email", email);
		Query queryNif = entman.createQuery("FROM UserJPA b WHERE b.nif = :nif").setParameter("nif", nif);

		if (!queryNif.getResultList().isEmpty())
		{
			return 1;				
		}
		else if (!queryEmail.getResultList().isEmpty())
		{
			return 2;
		}
		else
		{
			entman.persist(new UserJPA(nif, name, surname, phone, password, email));
			return 0;
		}
	}

	/**
	 * Mètode que s'utilitza per a modificar les dades personals d'un usuari.
	 * @param nif Nif de l'usuari logejat al sistema.
	 * @param name Nom de l'usuari logejat al sistema.
	 * @param surname Cognom de l'usuari logejat al sistema.
	 * @param phone Telefon de l'usuari logejat al sistema.
	 * @param password Contrasenya de l'usuari logejat al sistema.
	 * @param email Email de l'usuari logejat al sistema.
	 * 
	 * @return true Les dades s'han actualitzat correctament,
	 * @return false Les dades no s'han actualitzat correctament, s'ha intentat actualitzar el email ocupant el mateix nom que l'email d'un altre usuari ja existent
	 */
	@Override
	public boolean updatePersonalData(String nif, String name, String surname, String phone, String password, String email) 
	{

		UserJPA userLogejat = entman.find(UserJPA.class, nif);
			
		@SuppressWarnings("unchecked")
		List<UserJPA> userEmail = entman.createQuery("FROM UserJPA b WHERE b.email = :email").setParameter("email", email).getResultList();
		
		
		if(userEmail.isEmpty())//El email no existeix al sistema, per tant es pot actualitzar les dades sense problema.
		{
			userLogejat.setNif(nif);
			userLogejat.setName(name);
			userLogejat.setSurname(surname);
			userLogejat.setPhone(phone);
			userLogejat.setPassword(password);
			userLogejat.setEmail(email);

			entman.persist(userLogejat);
			return true;
			
		}
		
	
		if(userEmail.get(0).getEmail().equals(userLogejat.getEmail()))/*Si no es modifica el email, desde el usuari correcte, tambe es poden actualitzar les dades sense problema*/
		{
			userLogejat.setNif(nif);
			userLogejat.setName(name);
			userLogejat.setSurname(surname);
			userLogejat.setPhone(phone);
			userLogejat.setPassword(password);
			userLogejat.setEmail(email);

			entman.persist(userLogejat);
			return true;
			
		}
		
		/*Si no es compleixen els casos anteriors vol dir que que s'esta canviant el email per el d'algun usuari que ja existeix per tant no actualitzem les dades*/
		return false;
	}	

	/*
	 * Retorna l'user trobat en la bbdd. El busca a partir del nif
	 */
	@Override
	public UserJPA findUser(String nif) {
		return entman.find(UserJPA.class, nif);
	}	


	@Override
	public String login(String email, String pwd) {						
		try {

			Query query = entman.createQuery("FROM UserJPA b WHERE b.email = :email and b.password = :pwd");
			query.setParameter("email", email);
			query.setParameter("pwd", pwd);

			@SuppressWarnings("unchecked")
			Collection<UserJPA> users = query.getResultList();

			// Si usuari i el pwd existeix retorna true sino false
			if ( !users.isEmpty()  ||  users.size() > 0 ) {

				Iterator<UserJPA> iter = users.iterator();
				UserJPA element = (UserJPA) iter.next();

				return element.getNif();
			} else {
				return "NOVALID";
			}

		} catch(PersistenceException e) {

		}

		return "NOVALID";
	}

	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public Collection<TalkedLanguageJPA> listAllTalkedLanguages(String nif) {
		@SuppressWarnings("unchecked")
		Collection<TalkedLanguageJPA> allLanguages = entman.createQuery("FROM TalkedLanguageJPA a WHERE a.user.nif = :nif").setParameter("nif",nif).getResultList();
		return allLanguages;
	}

	/**
	 * Metode que afegeix les dades d'un idioma per a un determinat nif
	 */
	@Override
	public int addTalkedLanguage(String nif, String language, String level, String description) throws PersistenceException {
		try
		{
			Query queryNifLang= entman.createQuery("FROM TalkedLanguageJPA t WHERE t.user.nif = :nif AND t.language = :language").setParameter("nif", nif).setParameter("language", language);

			if (!queryNifLang.getResultList().isEmpty())
			{
				return 1;				
			}
			else
			{
				TalkedLanguageJPA talkedLanguage = new TalkedLanguageJPA(language, level, description); 
				talkedLanguage.setUser(entman.find(UserJPA.class, nif));
				entman.persist(talkedLanguage);
				return 0;
			}

		}catch (PersistenceException e) {
			throw e;
		} 
	}

	/**
	 * Metode que elimina un idioma donat per a un nif
	 */
	@Override
	public void deleteTalkedLanguage(String nif, String language) throws PersistenceException {
		try
		{
			int deletedCount = entman.createQuery("DELETE FROM TalkedLanguageJPA t WHERE t.user.nif = :nif AND t.language = :language").setParameter("nif", nif).setParameter("language", language).executeUpdate();
			if (deletedCount <= 0)
			{
				throw new PersistenceException("No s'ha pogut eliminar.");
			}

		}catch (PersistenceException e) {
			throw e;
		} 
	}

	/**
	 * Metode que afegeix les dades d'un idioma d'interes per a un determinat nif
	 */
	@Override
	public int addLanguageToTalk(String nif, String language, String level, String description, boolean acceptPay) throws PersistenceException {
		try
		{
			Query queryNifLang= entman.createQuery("FROM LanguageToTalkJPA l WHERE l.user.nif = :nif AND l.language = :language").setParameter("nif", nif).setParameter("language", language);

			if (!queryNifLang.getResultList().isEmpty())
			{
				return 1;				
			}
			else
			{
				LanguageToTalkJPA languageToTalk = new LanguageToTalkJPA(language, level, description, acceptPay); 
				languageToTalk.setUser(entman.find(UserJPA.class, nif));
				entman.persist(languageToTalk);
				return 0;
			}

		}catch (PersistenceException e) {
			throw e;
		} 
	}

	/**
	 * Metode que elimina un idioma d'interes donat per a un nif
	 */
	@Override
	public void deleteLanguageToTalk(String nif, String language) throws PersistenceException {
		try
		{
			int deletedCount = entman.createQuery("DELETE FROM LanguageToTalkJPA l WHERE l.user.nif = :nif AND l.language = :language").setParameter("nif", nif).setParameter("language", language).executeUpdate();
			if (deletedCount <= 0)
			{
				throw new PersistenceException("No s'ha pogut eliminar.");
			}

		}catch (PersistenceException e) {
			throw e;
		} 
	}

	/*
	 * Metode que llista els llenguatges que vol parlar un usuari en concret
	 * */
	@Override
	public Collection<LanguageToTalkJPA> listAllLanguagesToTalk(String nif) 
	{
		@SuppressWarnings("unchecked")
		Collection<LanguageToTalkJPA> allLanguagesToTalk = entman.createQuery("FROM LanguageToTalkJPA a WHERE a.user.nif = :nif").setParameter("nif",nif).getResultList();
		return allLanguagesToTalk;
	}

	@Override
	public boolean findLanguageToTalkAppointment(String nif, String language) {
		// TODO Auto-generated method stub

		Collection<?> findtalkappointment = entman.createQuery("FROM TalkAppointmentJPA a WHERE a.languageToTalk.user.nif = :usernif and a.languageToTalk.language = :language").setParameter("usernif", nif).setParameter("language", language).getResultList();
		
		if ( findtalkappointment.size() > 0) {
			return true;
		} else {
			return false;
		}
	
	}

}
