package ejb;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import jpa.DeniedRequestJPA;
import jpa.LanguageToTalkJPA;
import jpa.LocationJPA;
import jpa.TalkAppointmentJPA;
import jpa.TalkStatus;
import jpa.UserJPA;

/**
 * EJB Implementa els metodes accès a la base de dades de les cites
 * 
 * @author Grup 6
 * @version 1.0
 *
 */
//@Stateless: Indiquem que es tracta de un EJB Session sense estat.
@Stateless
public class TalkAppointmentAdminBean implements TalkAppointmentAdminFacadeRemote, TalkAppointmentAdminFacade {

	
	/**
	 * Indica el context per la persistencia en la Base Dades JPA
	 */
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	/**
	 * EJB Facade
	 */
	@EJB
	private UserFacade userFacade;

	/**
	 * Dona alta una cita
	 * 
	 * @param description comentari de la cita
	 * @param Location Objecte localitzacio
	 * @param date data de la cita
	 * @param time hora de la cita
	 * @param languageToTalk llenguatge de la cita
	 */
	public void addTalkAppointment(String description, LocationJPA location, Date date, Time time, LanguageToTalkJPA languageToTalk){

		try{		
			
			try {
				location.setId(this.getLocationSequence());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			entman.persist(location);
			
			TalkAppointmentJPA talkApp = new TalkAppointmentJPA(description,date,time,TalkStatus.OPEN);
						
			// Recupero el nif i inserta un registre a la base de dades.
						
			talkApp.setLocation(location);
			talkApp.setUserPublish(languageToTalk.getUser());
			//talkApp.setUserLanguageToTalk(languageToTalk.getUser().getNif());			
			talkApp.setLanguageToTalk(languageToTalk); 
			
			talkApp.setDate(date);
			talkApp.setTime(time);
			talkApp.setDescription(description);
			talkApp.setStatus(TalkStatus.OPEN);
			
			
			try {				
				talkApp.setId(this.getTalkAppointmentSequence());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
			entman.persist(talkApp);
						

		} catch(PersistenceException e){
			throw e;
		}

	}

	/**
	 * Metode per Acceptar una peticio de conversa
	 * 
	 * @param talkid identificador de cita
	 * @param nif identificador del usuari
	 *
	 */
	@Override
	public void acceptRequest(Long talkid, String nif) throws PersistenceException {
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
								
				if ( talkAppointment.getUserPublish().getNif().compareTo(nif) == 0 ) {
					talkAppointment.setStatus(TalkStatus.CONFIRMED);
				}
				
				entman.persist(talkAppointment);
			}

		}catch (PersistenceException e) {
			throw e;
		} 
	}

	/**
	 * Metode Per Rebutjar la peticio de conversa
	 * 
	 * @param talkid identificador de conversa
	 * @param nif identificador usuari
	 * @param reason Motiu per rebutjar la cita 
	 */
	@Override
	public void rejectRequest(Long talkid, String nif, String reason) {
		// TODO Auto-generated method stub
		
		TalkAppointmentJPA talkApp = entman.find(TalkAppointmentJPA.class, talkid);
				
		if ( talkApp.getUserPublish().getNif().compareTo(nif) == 0 ) {
			
		   DeniedRequestJPA deniedRequest = new DeniedRequestJPA(reason); 
		   deniedRequest.setUser(entman.find(UserJPA.class, nif));
		   deniedRequest.setTalkApp(talkApp);
		   
		   try {
			deniedRequest.setId(this.getDeniedRequestSequence());
		   } catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
		   
		   entman.persist(deniedRequest);
		   		   		   
		   talkApp.setUserSign(null);
		   talkApp.setStatus(TalkStatus.OPEN);
		   entman.persist(talkApp);			
		}
		
	}

	
	/*
	@Override
	public int rejectRequest(Long talkid, String nif, String reason) throws PersistenceException {
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

				if (nif == "" || queryNif.getResultList().isEmpty())
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
					talkApp.setStatus(TalkStatus.OPEN);
					entman.persist(talkApp);
					return 0;
				}
			}
			
		}catch (PersistenceException e) {
			throw e;
		} 
	}
	*/

	/**
	 * Retorna les peticions publicades per un usuari
	 * 
	 * @param nif identificador usuari
	 * @return talkAppointments retorna la llista de cita d'un usuari
	 * 
	 */
	@Override
	public Collection<?> findMyTalkAppointmentsAsProposal(String nif) {
		// TODO Auto-generated method stub
		Query query = null;
		

		query = entman.createQuery("FROM TalkAppointmentJPA t WHERE t.userPublish.nif = :nif order by t.date , t.time");
		query.setParameter("nif", nif);

		@SuppressWarnings("unchecked")
		Collection<TalkAppointmentJPA> talkAppointments = query.getResultList();
		
		
		return talkAppointments;
	}
	
	/**
	 * Genera el id per a la taula location
	 * @return identificador
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getTalkAppointmentSequence() throws Exception {
		Query query = null;
		
		query = entman.createNativeQuery("SELECT nextval('practicalcase.talkappointment_idtalkapp_seq')");
		return ((BigInteger) query.getResultList().get(0)).longValue();		
	}
	
	/**
	 * Genera el id per a a taula location
	 * @return
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getLocationSequence() throws Exception {
		Query query = null;
		
		query = entman.createNativeQuery("SELECT nextval('practicalcase.location_idlocation_seq')");
		return ((BigInteger) query.getResultList().get(0)).longValue();		
	}
	
	/**
	 * Genera el id per a a taula DeniedRequest
	 * @return
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getDeniedRequestSequence() throws Exception {
		Query query = null;
		
		query = entman.createNativeQuery("SELECT nextval('practicalcase.deniedrequest_iddeniedrequest_seq')");
		return ((BigInteger) query.getResultList().get(0)).longValue();		
	}
	
}
