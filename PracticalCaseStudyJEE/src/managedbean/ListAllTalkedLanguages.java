package managedbean;
import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.faces.context.FacesContext;
import ejb.UserFacadeRemote;
//import ejb.UserFacade;
import jpa.TalkedLanguageJPA;

@ManagedBean(name = "ListTalkedLang")
@SessionScoped
public class ListAllTalkedLanguages implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nif; // Havia protected
	protected Collection<TalkedLanguageJPA> languageList;
	
	@EJB
	private UserFacadeRemote userRemote;
	
	public ListAllTalkedLanguages() 
	{
	}
	
	public Collection<TalkedLanguageJPA> getLanguageList() throws Exception
	{
		
		this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());	
		
		//languageList = userLocalFacade.listAllTalkedLanguages("00000000X");
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		
		languageList = userRemote.listAllTalkedLanguages(this.getNif());
		return languageList;
	}
	
	public void setLanguageList(Collection<TalkedLanguageJPA> languageList)
	{
		this.languageList = languageList;
	}
	
	public void setNif(String nif){
		this.nif= nif;
	}
	
	public String getNif(){
		return nif;
	}

}
