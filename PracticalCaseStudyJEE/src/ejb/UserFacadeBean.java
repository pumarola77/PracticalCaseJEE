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
			Query queryNif = entman.createQuery("FROM UserJPA b WHERE b.nif = :nif").setParameter("nif", nif);
			if (!queryNif.getResultList().isEmpty())
			{
				//entman.createQuery("UPDATE UserJPA b SET b.nif = :nif, b.name = :name, b.surname = :surname: b.phone = :phone, b.password = :password, b.email = :email").setParameter("nif", nif).setParameter("name", name).setParameter("surname", surname).setParameter("phone", phone).setParameter("password", password).setParameter("email", email);
				entman.createQuery("UPDATE UserJPA u SET u.name = :name WHERE u.nif = :nif").setParameter("nif", nif).setParameter("name", name);
				entman.createQuery("UPDATE UserJPA u SET u.surname = :surname WHERE u.nif = :nif").setParameter("nif", nif).setParameter("surname", surname);
				entman.createQuery("UPDATE UserJPA u SET u.phone = :phone WHERE u.nif = :nif").setParameter("nif", nif).setParameter("phone", phone);
				entman.createQuery("UPDATE UserJPA u SET u.password = :password WHERE u.nif = :nif").setParameter("nif", nif).setParameter("password", password);
				entman.createQuery("UPDATE UserJPA u SET u.email = :email WHERE u.nif = :nif").setParameter("nif", nif).setParameter("email", email);
				return true;				
			}
			else
			{
				return false;
			}
		}	
		
		/*
		 * Retorna l'user trobat en la bbdd. El busca a partir del nif
		 */
		@Override
		public UserJPA findUser(String nif)throws PersistenceException {
			UserJPA user = null;
			try
			{
				@SuppressWarnings("unchecked")
				Collection<UserJPA> users = entman.createQuery("FROM UserJPA b WHERE b.nif = :nif").setParameter("nif",nif).getResultList();
				if (!users.isEmpty() || users.size()==1)
				{
					Iterator<UserJPA> iter =users.iterator();
					user = (UserJPA) iter.next();				
				}
			}catch (PersistenceException e) {
				
			} 
		    return user;
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
			Collection<TalkedLanguageJPA> allLanguages = entman.createQuery("FROM TalkedLanguageJPA a WHERE a.nif = :nif").setParameter("nif",nif).getResultList();
			return allLanguages;
		}
	
}
