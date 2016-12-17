package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.TalkAppointmentFacadeRemote;
import ejb.UserFacadeRemote;
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
	private int id;
	
	public ShowTalkAppointmentMBean() throws Exception{
		/*int auxId;
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		showTalkAppointmentRemote = (TalkAppointmentFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/TalkAppointmentBean!ejb.TalkAppointmentFacadeRemote");
		
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("id") == true) {
			auxId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id").toString().replaceAll(" ",""));
			this.setId(auxId);
			//Busquem el TalkAppointment corresponent
			setTalkAppointment(getId());
		}*/
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public TalkAppointmentJPA getTalkAppointment(){
		return talkAppointment;
	}
	
	public void setTalkAppointment(int id)  throws Exception{
		talkAppointment = (TalkAppointmentJPA) showTalkAppointmentRemote.showTalkAppointment(id);		
	}
	
	public String showView(int id) throws Exception{
		this.setTalkAppointment(id);
		return "showTalkAppointmentView";
	}
	
}
