package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import ejb.UserFacadeRemote;
import ejb.UserFacade;
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

		@Override
		public boolean updatePersonalData(String nif, String name, String surname, String phone, String password,
				String email) {
			
			//Iniciar transaccio
			entman.getTransaction().begin();
			//Buscar registre a actualitzar
			UserJPA user = entman.find(UserJPA.class, nif);
			//Si l'usuari existeix, actualitzem les seves dades i retornem true
			if(user != null){
				//Actualitzar dades amb els parameteres d'entrada
				user.setNif(nif);
				user.setName(name);
				user.setSurname(surname);
				user.setPhone(phone);
				user.setPassword(password);
				user.setEmail(email);
				//Pujem el registre al UserJPA
				entman.getTransaction().commit();
				//Finalitzem la transaccio
				entman.close();
				return true;
			}
			//Si l'usuari no existeix retornem false
			else{
				return false;
			}

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
	
}
