package ejb;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.PetJPA;
import jpa.TalkAppointmentJPA;

//@Stateless: Indiquem que es tracta de un EJB Session sense estat.
@Stateless
public class TalkAppointmentBean implements TalkAppointmentFacadeRemote, TalkAppointmentFacade {
	
	//@PersistenceContext: Indiquem quin context per la persistencia utilitzara el EntityManager
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;

	
	public TalkAppointmentJPA showTalkAppointment(int id) throws PersistenceException{
		TalkAppointmentJPA talkapp = null;
		try
		{
			@SuppressWarnings("unchecked")
			Collection<TalkAppointmentJPA> talkapps = entman.createQuery("FROM TalkAppointmentJPA b WHERE b.id = ?1").setParameter(1, new Integer(id)).getResultList();
			if (!talkapps.isEmpty() || talkapps.size()==1)
			{
				Iterator<TalkAppointmentJPA> iter =talkapps.iterator();
				talkapp = (TalkAppointmentJPA) iter.next();				
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return talkapp;
	}
}
