package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;


import ejb.UserFacadeRemote;
import ejb.UserFacade;
import jpa.UserJPA;

//@Stateless: Indiquem que es tracta de un EJB Session sense estat.
@Stateless
public class UserFacadeBean implements UserFacadeRemote, UserFacade{

		//@PersistenceContext: Indiquem quin context per la persistencia utilitzara el EntityManager
		@PersistenceContext(unitName="PracticalCase") 
		private EntityManager entman;

		@Override
		public boolean registerUser(String nif, String name, String surname, String phone, String password, String email) 
		{
			/*Si l'usuari identificat per nif no existeix a la base de dades es crea i es retorna true conforme ha funcionat correctament*/
			if (entman.find(UserJPA.class, nif) == null )
			{
				entman.persist(new UserJPA(nif, name, surname, phone, password, email));
				return true;
			}
			/*Si l'usuari ja existeix es retorna false ja que es considera que es un error*/
			else 
			{
				return false;
			}
			
		}
	
		
	
}
