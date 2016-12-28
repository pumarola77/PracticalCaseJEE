package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.TalkAppointmentFacadeRemote;
import ejb.UserFacadeRemote;
import jpa.LocationJPA;
import jpa.TalkAppointmentJPA;

@ManagedBean(name = "ShowTalkAppointment")
@ViewScoped
public class ShowTalkAppointmentMBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private TalkAppointmentFacadeRemote showTalkAppointmentRemote;
	/*
	@EJB
	private TalkAppointmentFacade showTalkAppointmentLocal;
	 */
	private TalkAppointmentJPA talkAppointment;
	private Long id;
	private String returnView;

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

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getReturnView(){
		return returnView;
	}

	public void setReturnView(String returnView){
		this.returnView = returnView;
	}

	public TalkAppointmentJPA getTalkAppointment(){
		return talkAppointment;
	}

	public void setTalkAppointment(Long id)  throws Exception{
		talkAppointment = (TalkAppointmentJPA) showTalkAppointmentRemote.showTalkAppointment(id);		
	}

	public void showView() throws Exception{
		this.setTalkAppointment(this.getId());
	}

	public String returnView(String returnView) throws Exception{
		return returnView + "?faces-redirect=true";
	}

}
