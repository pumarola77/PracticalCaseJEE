package managedbean;
import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.faces.context.FacesContext;
import ejb.UserFacadeRemote;
import jpa.TalkedLanguageJPA;

@ManagedBean(name = "ListTalkedLang")
@ViewScoped
public class ListAllTalkedLanguages implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nif; // Havia protected
	
	/*Aquesta variable contindra el llistat complert de llenguatges de l'usuari*/
	private Collection<TalkedLanguageJPA> languageFullList;
	
	/*Aquesta variable contindra els 10 idiomes o menys que es mostren actualment per pantalla*/
	protected Collection<TalkedLanguageJPA> languageListView;
	
	/*Aquesta variable fa de contador per saber quin punt de la llista d'idiomes esta veient l'usuari*/
	private int screen;
	
	@EJB
	private UserFacadeRemote userRemote;
	
	
	/**
	 * Constructor
	 */	
	public ListAllTalkedLanguages() 
	{
		
		//Carrega el NIF del User
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == true) {
			this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());
		}
		
		screen=0;
	}
	
	/*Aquest metode s'utilitza per carregar el llistat complert d'idiomes de l'usuari*/
	private void loadLanguageList() throws Exception
	{
		
		this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());	
		
		//languageList = userLocalFacade.listAllTalkedLanguages("00000000X");
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		
		languageFullList = userRemote.listAllTalkedLanguages(this.getNif());
		
	}
	
	/*Aquest metode es el que s'accedeix desde la vista i s'utilitza per carregar el llistat d'idiomes reduit que s'ha de mostrar*/
	public Collection<TalkedLanguageJPA> getLanguageListView() throws Exception{
		
		int n=0;
		this.loadLanguageList();
		
		languageListView = new ArrayList<TalkedLanguageJPA>();
		
		Iterator<TalkedLanguageJPA> iter = languageFullList.iterator();
		
		while(iter.hasNext())
		{
			TalkedLanguageJPA aux = (TalkedLanguageJPA) iter.next();
			
			if (n>= screen*10 && n< (screen*10+10))
			{
				this.languageListView.add(aux);
			}
			n=n+1;
		}
		return languageListView;
	}
	
	public void nextScreen()
	{
		if (((screen+1)*10 < languageFullList.size()))
		{
			screen +=1;
		}
	}
	public void previousScreen()
	{
		if ((screen > 0))
		{
			screen -=1;
		}
	}
	
	public void setNif(String nif){
		this.nif= nif;
	}
	
	public String getNif(){
		return nif;
	}

}
