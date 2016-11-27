package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import ejb.UserFacadeRemote;
import ejb.UserFacade;
import jpa.PetJPA;
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

	/*
	 * retorna true: A trobat l'usuari i li ha actualitzat les dades
	 * retorna false: No a trobat l'usuari
	 */
	@Override
	public boolean updatePersonalData(String nif, String name, String surname, String phone, String password, String email) 
	{

		UserJPA user = entman.find(UserJPA.class, nif);
		user.setNif(nif);
		user.setName(name);
		user.setSurname(surname);
		user.setPhone(phone);
		user.setPassword(password);
		user.setEmail(email);

		entman.persist(user);
		return true;
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
	public void addTalkedLanguage(String nif, String language, String level, String description) throws PersistenceException {
		try
		{
			Query queryNifLang= entman.createQuery("FROM TalkedLanguageJPA t WHERE t.user.nif = :nif AND t.language = :language").setParameter("nif", nif).setParameter("language", language);

			if (!queryNifLang.getResultList().isEmpty())
			{
				// Mirem que no es repeteixi un idioma que ja estigui previament introduit.
				throw new PersistenceException("L'idioma <" + language + "> ja estava introduit amb anterioritat.");			
			}
			else
			{
				String strId = String.valueOf(entman.createQuery("SELECT MAX(t.id) FROM TalkedLanguageJPA t").getSingleResult());
				int MaxId = 0;
				if (strId != null)
				{ 
					// De moment faig el autonumeric per codi. Fins que decidim si fer-lo a la JPA.
					MaxId = Integer.parseInt(strId) + 1;
				}
				TalkedLanguageJPA talkedLanguage = new TalkedLanguageJPA(MaxId, language, level, description); 
				talkedLanguage.setUser(entman.find(UserJPA.class, nif));
				entman.persist(talkedLanguage);
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

}
