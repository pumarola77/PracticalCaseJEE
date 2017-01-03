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

/**
 * ManagedBean ShowTalkAppointment
 * 
 * @author Bazinga
 * @version 1.0
 *
 */
@ManagedBean(name = "ShowTalkAppointment")
@ViewScoped
public class ShowTalkAppointmentMBean implements Serializable{

	/**
	 * Obligatori perque la classe implementa serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * EJB TalkAppointmentFacadeRemote
	 */
	@EJB
	private TalkAppointmentFacadeRemote showTalkAppointmentRemote;

	/**
	 * talkappointment
	 */
	private TalkAppointmentJPA talkAppointment;
	
	/**
	 * identificador
	 */
	private Long id;
	
	/**
	 * vista
	 */
	private String returnView;

	/**
	 * Constructor
	 * @throws Exception incidencia al constructor
	 */
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

	/**
	 * Getter Id
	 * @return id
	 */
	public Long getId(){
		return id;
	}

	/**
	 * Setter id
	 * @param id identificador
	 */
	public void setId(Long id){
		this.id = id;
	}

	/**
	 * Getter returnView
	 * @return returnView
	 */
	public String getReturnView(){
		return returnView;
	}

	/**
	 * Setter returnView
	 * @param returnView returnView
	 */
	public void setReturnView(String returnView){
		this.returnView = returnView;
	}

	/**
	 * Getter TalkAppointment
	 * @return talkappointment
	 */
	public TalkAppointmentJPA getTalkAppointment(){
		return talkAppointment;
	}

	/**
	 * Setter TalkAppointment
	 * @param id identificador
	 * @throws Exception carregar cita
	 */
	public void setTalkAppointment(Long id)  throws Exception{
		talkAppointment = (TalkAppointmentJPA) showTalkAppointmentRemote.showTalkAppointment(id);		
	}

	/**
	 * ShowView
	 * @throws Exception incidencia al mostrar cita
	 */
	public void showView() throws Exception{
		this.setTalkAppointment(this.getId());
	}

	/**
	 * Retorna vista
	 * @param returnView identificador vista
	 * @return vista
	 * @throws Exception incidencia en la vista
	 */
	public String returnView(String returnView) throws Exception{
		return returnView + "?faces-redirect=true";
	}

}
