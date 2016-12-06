package ejb;

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
		// TODO Auto-generated method stub
		try {

			int deleteCount = entman.createQuery("DELETE FROM TalkAppointmentJPA b WHERE b.userSign.nif = :nif and b.id = :talkid").setParameter("nif", nif).setParameter("id", talkid).executeUpdate();

			if (deleteCount <= 0 ) {
				throw new PersistenceException("No s'ha pogut eliminar.");
			}

		} catch (PersistenceException ex) {
			throw ex;
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

}
