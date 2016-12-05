package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.TalkAppointmentFacadeRemote;
import jpa.LocationJPA;
import jpa.TalkAppointmentJPA;

@ManagedBean(name = "ShowTalkAppointment")
@SessionScoped
public class ShowTalkAppointmentMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private TalkAppointmentFacadeRemote showTalkAppointmentRemote;
	/*
	@EJB
	private TalkAppointmentFacade showTalkAppointmentLocal;
	*/
	private TalkAppointmentJPA talkAppointment;

	public ShowTalkAppointmentMBean(){
		
	}
	
	public TalkAppointmentJPA getTalkAppointment(){
		return talkAppointment;
	}
	
	public void setTalkAppointment(int id)  throws Exception{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);

		showTalkAppointmentRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		talkAppointment = (TalkAppointmentJPA) showTalkAppointmentRemote.showTalkAppointment(id);
	}
	
	public String getLocationTalkAppointment(){
		LocationJPA loc = getTalkAppointment().getLocation();
		return loc.toString();
	}
}
