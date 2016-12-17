package ejb;

import java.util.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import jpa.TalkAppointmentJPA;
import jpa.UserJPA;


//@Stateless: Indiquem que es tracta de un EJB Session sense estat.
@Stateless
public class TalkAppointmentBean implements TalkAppointmentFacadeRemote, TalkAppointmentFacade {

	//@PersistenceContext: Indiquem quin context per la persistencia utilitzara el EntityManager
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	@EJB
	private UserFacade userFacade;

	public TalkAppointmentJPA showTalkAppointment(int id) throws PersistenceException{

		TalkAppointmentJPA talkApp = entman.find(TalkAppointmentJPA.class, id);
		return talkApp;
	}


	@Override
	public void registerInTalkAppointment(String nif, Integer talkid) {
		// TODO Auto-generated method stub

		Query query = entman.createQuery("FROM TalkAppointmentJPA b WHERE b.id = :id");
		query.setParameter("id", talkid);

		@SuppressWarnings("unchecked")
		Collection<TalkAppointmentJPA> talkappointments = query.getResultList();

		if ( !talkappointments.isEmpty() || talkappointments.size() > 0 ) {

			Iterator<TalkAppointmentJPA> iter = talkappointments.iterator();
			TalkAppointmentJPA element = (TalkAppointmentJPA) iter.next();

			UserJPA userSign = userFacade.findUser(nif);
			element.setUserSign(userSign);

			entman.persist(element);
		}

	}

	@Override
	public void removeFromTalkAppointment(String nif, Integer talkid) {

		/*try {

			int deleteCount = entman.createQuery("DELETE FROM TalkAppointmentJPA b WHERE b.userSign.nif = :nif and b.id = :talkid").setParameter("nif", nif).setParameter("id", talkid).executeUpdate();

			if (deleteCount <= 0 ) {
				throw new PersistenceException("No s'ha pogut eliminar.");
			}

		} catch (PersistenceException ex) {
			throw ex;
		}*/
		
		
		TalkAppointmentJPA aux = entman.find(TalkAppointmentJPA.class, talkid);
		
		/*Si el TalkAppointment ja te el camp UserSign com a null no cal seguir executant aquest metode*/
		if(aux.getUserSign()!=null)
		{
			//Comprovem que l'usuari logejat al sistema domes es pugui desapuntar a les peticions a les quals ell mateix esta apuntat.
			if(nif.equals(aux.getUserSign().getNif()))
			{
				aux.setUserSign(null);
				entman.persist(aux);
			}
		}	

	}

	@Override
	public Collection<TalkAppointmentJPA> findMyTalkAppointments(String nif, boolean isPublisher){
		Query query = null;
		
		if (isPublisher) {
			query = entman.createQuery("FROM TalkAppointmentJPA t WHERE t.userPublish.nif = :nif");
		} else {
			query = entman.createQuery("FROM TalkAppointmentJPA t WHERE t.userSign.nif = :nif");	
		}
		query.setParameter("nif", nif);

		@SuppressWarnings("unchecked")
		Collection<TalkAppointmentJPA> myTalkAppointments = query.getResultList();

		return myTalkAppointments;
	}


	@Override
	public Collection<?> findTalkAppointments(String location,String fecha,String hora,String language) throws PersistenceException {
		// TODO Auto-generated method stub
		String select=null;
		Query query=null;
				
		try {

			if ( location.compareTo("ALL CITIES") == 0 ) {
				select = "FROM TalkAppointmentJPA b WHERE b.status = 'OPEN'";
			} else {
				select = "FROM TalkAppointmentJPA b WHERE b.status = 'OPEN' and b.location.city = :ciutat";
				
				if ( language.compareTo("ALL LANGUAGES") != 0 ) {
					select = select + " AND b.languageToTalk.language = :language";
				}
				
				if ( fecha.compareTo("ALL DATES") != 0 ) {
					select = select + " AND b.date = :date";
				}
				
				if ( hora.compareTo("ALL HOURS") != 0 ) {
					select = select + " AND b.time = :time";
				}
				
			}
			
			//Order By
			select = select + " ORDER BY b.location.city , b.languageToTalk.language , b.date , b.time";
			
			query = entman.createQuery(select);
			
			if ( location.compareTo("ALL CITIES") != 0 ) {
				query.setParameter("ciutat", location);
			}
			
			if ( language.compareTo("ALL LANGUAGES") != 0 ) {
				query.setParameter("language", language);
			}
			
			if ( fecha.compareTo("ALL DATES") != 0 ) {
				
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
				Date date = (Date) formatDate.parse(fecha);
				
				query.setParameter("date", date);
			}
			
			if ( hora.compareTo("ALL HOURS") != 0 ) {
				
				DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
				java.sql.Time timeValue = new java.sql.Time(formatter.parse(hora).getTime());
								
				query.setParameter("time", timeValue);
			}
			
			@SuppressWarnings("unchecked")
			Collection<TalkAppointmentJPA> talkAppointments = query.getResultList();
						
			return talkAppointments;
			
		} catch (PersistenceException e) {
			System.out.println(e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}


	@Override
	public Collection<?> citiesTalkAppointments() {
		// TODO Auto-generated method stub
		
		try {

			@SuppressWarnings("unchecked")
			Collection<String> talkappointments = entman.createQuery("SELECT a.location.city FROM TalkAppointmentJPA a WHERE a.status = 'OPEN' GROUP BY a.location.city").getResultList();
			
			if ( !talkappointments.isEmpty() ) {
				return talkappointments;
			}
			
		} catch(PersistenceException e) {
			System.out.println(e);
		}
		
		return null;
	}
	
	@Override
	public Collection<?> languagesfromTalkAppointments(String city) {
		// TODO Auto-generated method stub
		
		try {
			
			@SuppressWarnings("unchecked")
			Collection<String> talkappointments = entman.createQuery("SELECT a.languageToTalk.language  FROM TalkAppointmentJPA a WHERE a.status = 'OPEN' and a.location.city = :ciutat GROUP BY a.languageToTalk.language").setParameter("ciutat", city).getResultList();
			
			if ( !talkappointments.isEmpty()) {
				return talkappointments;
			}
			
			
		} catch(PersistenceException e) {
			System.out.println(e);
		}
		
		return null;
	}
	
	@Override
	public Collection<?> datefromTalkAppointmments(String city, String language) {
		// TODO Auto-generated method stub	
		
		try {
			
			@SuppressWarnings("unchecked")
			Collection<Date> talkappointments = entman.createQuery("SELECT a.date FROM TalkAppointmentJPA a where a.status = 'OPEN' and a.location.city = :ciutat AND a.languageToTalk.language = :language GROUP BY a.date").setParameter("ciutat", city).setParameter("language",language).getResultList();
			
			if ( !talkappointments.isEmpty() ) {
				return talkappointments;
			}
			
		} catch(PersistenceException e) {
			System.out.println(e);
		}
		
		return null;
		
	}


	@Override
	public Collection<?> timefromTalkAppointments(String city,String language,Date date) {
		// TODO Auto-generated method stub
		
		try {
			
			@SuppressWarnings("unchecked")
			Collection<Time> talkappointments = entman.createQuery("SELECT a.time FROM TalkAppointmentJPA a WHERE a.status = 'OPEN' and a.location.city = :ciutat and a.languageToTalk.language = :language and a.date = :date GROUP BY a.time").setParameter("ciutat", city).setParameter("language",language).setParameter("date",date).getResultList();
			
			if ( !talkappointments.isEmpty()) {
				return talkappointments;
			}
			
		} catch(PersistenceException e) {
			System.out.println(e);
		}
		
		return null;
	}




}
