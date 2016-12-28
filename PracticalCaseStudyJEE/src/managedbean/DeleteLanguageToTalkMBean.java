package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;

/**
 * 
 * Hi han els metodes relacionats amb esborrar els llenguatges que vol
 * parlar un usuari registrat.
 * 
 * @author Grup6
 * @version	%I% %G%
 * @since 1.8
 *
 */
@ManagedBean(name = "languagetotalkdelete")
@ViewScoped
public class DeleteLanguageToTalkMBean implements Serializable{

	/**
	 * Variable obligatoria per el serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * EJB
	 */
	@EJB
	private UserFacadeRemote deleteLanguageToTalkRemote;

	/**
	 * Metode que es fa servir per eliminar un llenguatge que vol
	 * parlar un usuari
	 * 
	 * @param nif identificador del usuari registrat
	 * @param language identificador del llenguatge
	 * @return Vista
	 * @throws Exception
	 */
	public String deleteLanguageToTalk(String nif, String language) throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		deleteLanguageToTalkRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
	
		deleteLanguageToTalkRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		
		Boolean teregistre = deleteLanguageToTalkRemote.findLanguageToTalkAppointment(nif, language);
				
		if ( teregistre == false ) {
			deleteLanguageToTalkRemote.deleteLanguageToTalk(nif, language);
		}
	
		return "LanguagesToTalkListView";
	}
}
