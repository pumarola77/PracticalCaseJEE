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
import jpa.LanguageToTalkJPA;
import jpa.TalkedLanguageJPA;

@ManagedBean(name = "ListLangToTalk")
@SessionScoped
public class ListAllLanguagesToTalk implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nif; 
	
	/*Aquesta variable contindra el llistat complert de llenguatges que vol parlarl'usuari*/
	private Collection<LanguageToTalkJPA> languageToTalkFullList;
	
	/*Aquesta variable contindra els 10 idiomes o menys que es mostren actualment per pantalla*/
	protected Collection<LanguageToTalkJPA> languageToTalkListView;
	
	/*Aquesta variable fa de contador per saber quin punt de la llista d'idiomes esta veient l'usuari*/
	private int screen;
	
	@EJB
	private UserFacadeRemote userRemote;
	
	public ListAllLanguagesToTalk()
	{
		screen=0;
	}
	
	private void loadLanguageToTalkList() throws Exception
	{
		
		this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());	
		
		//languageList = userLocalFacade.listAllTalkedLanguages("00000000X");
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		
		languageToTalkFullList = userRemote.listAllLanguagesToTalk(this.getNif());
		
	}
	
	/*Aquest metode es el que s'accedeix desde la vista i s'utilitza per carregar el llistat d'idiomes reduit que s'ha de mostrar*/
	public Collection<LanguageToTalkJPA> getLanguageToTalkListView() throws Exception{
		
		int n=0;
		this.loadLanguageToTalkList();
		
		languageToTalkListView = new ArrayList<LanguageToTalkJPA>();
		
		Iterator<LanguageToTalkJPA> iter = languageToTalkFullList.iterator();
		
		while(iter.hasNext())
		{
			LanguageToTalkJPA aux = (LanguageToTalkJPA) iter.next();
			
			if (n>= screen*10 && n< (screen*10+10))
			{
				this.languageToTalkListView.add(aux);
			}
			n=n+1;
		}
		return languageToTalkListView;
	}
	
	public void nextScreen()
	{
		if (((screen+1)*10 < languageToTalkFullList.size()))
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
