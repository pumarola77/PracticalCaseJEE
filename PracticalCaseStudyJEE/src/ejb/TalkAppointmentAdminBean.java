package ejb;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import jpa.DeniedRequestJPA;
import jpa.LanguageToTalkJPA;
import jpa.LocationJPA;
import jpa.TalkAppointmentJPA;
import jpa.TalkStatus;
import jpa.TalkedLanguageJPA;
import jpa.UserJPA;

//@Stateless: Indiquem que es tracta de un EJB Session sense estat.
@Stateless
public class TalkAppointmentAdminBean implements TalkAppointmentAdminFacadeRemote, TalkAppointmentAdminFacade {

	//@PersistenceContext: Indiquem quin context per la persistencia utilitzara el EntityManager
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	@EJB
	private UserFacade userFacade;

	public void addTalkAppointment(String description, LocationJPA location, Date date, Time time, LanguageToTalkJPA languageToTalk){


		try{							
			entman.persist(location);
			
			
			TalkAppointmentJPA talkApp = new TalkAppointmentJPA(description,date,time,TalkStatus.OPEN);
						
			// Recupero el nif i inserta un registre a la base de dades.
						
			talkApp.setLocation(location);
			talkApp.setUserPublish(languageToTalk.getUser());
			talkApp.setUserLanguageToTalk(languageToTalk.getUser().getNif());			
			talkApp.setLanguageToTalk(languageToTalk); 
			
			talkApp.setDate(date);
			talkApp.setTime(time);
			talkApp.setDescription(description);
			talkApp.setStatus(TalkStatus.OPEN);
			
			//Identificador s'ha de treure nomes serveix ara per verificar que es doni alta.
			talkApp.setId(1);			
			
			entman.persist(talkApp);
			

		} catch(PersistenceException e){
			throw e;
		}
		
	}

	/**
	 * Metode per acceptar una petició de conversa
	 */
	@Override
	public void acceptRequest(int talkid, String nif) throws PersistenceException {
		try
		{
			Query queryTalkId= entman.createQuery("FROM TalkAppointmentJPA t WHERE t.id = :talkid").setParameter("talkid", talkid);

			if (queryTalkId.getResultList().isEmpty())
			{
				// Mirem que existeixi el TalkAppointment.
				throw new PersistenceException("La petició de conversa no existeix.");			
			}
			else
			{				
				TalkAppointmentJPA talkAppointment = entman.find(TalkAppointmentJPA.class, talkid); 
				talkAppointment.setUserSign(entman.find(UserJPA.class, nif));
				talkAppointment.setStatus(TalkStatus.CONFIRMED);
				entman.persist(talkAppointment);
			}

		}catch (PersistenceException e) {
			throw e;
		} 
	}

	/**
	 * Metode per rebutjar una petició de conversa, on s'ha d'especificar la rao
	 */
	@Override
	public int rejectRequest(int talkid, String nif, String reason) throws PersistenceException {
		try
		{
			Query queryTalkId= entman.createQuery("FROM TalkAppointmentJPA t WHERE t.id = :talkid").setParameter("talkid", talkid);

			if (queryTalkId.getResultList().isEmpty())
			{
				// Mirem que existeixi el TalkAppointment.
				return 1;
			}
			else
			{
				Query queryNif= entman.createQuery("FROM UserJPA u WHERE u.nif = :nif").setParameter("nif", nif);

				if (queryNif.getResultList().isEmpty())
				{
					// Mirem que existeixi l'usuari.
					return 2;
				}
				else
				{
					TalkAppointmentJPA talkApp = entman.find(TalkAppointmentJPA.class, talkid);
					DeniedRequestJPA deniedRequest = new DeniedRequestJPA(reason); 
					deniedRequest.setUser(entman.find(UserJPA.class, nif));
					deniedRequest.setTalkApp(talkApp);
					entman.persist(deniedRequest);
					talkApp.setUserSign(null);
					entman.persist(talkApp);
					return 0;
				}
			}
			
		}catch (PersistenceException e) {
			throw e;
		} 
	}

	/**
	 * Retorna les peticions publicades per un usuari
	 * 
	 */
	@Override
	public Collection<?> findMyTalkAppointmentsAsProposal(String nif) {
		// TODO Auto-generated method stub
		Query query = null;
		

		query = entman.createQuery("FROM TalkAppointmentJPA t WHERE t.userPublish.nif = :nif");
		query.setParameter("nif", nif);

		@SuppressWarnings("unchecked")
		Collection<TalkAppointmentJPA> talkAppointments = query.getResultList();
		
		
		return talkAppointments;
	}
}
