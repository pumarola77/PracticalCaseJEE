package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;

/**
 * Managed Bean DeleteLanguageToTalkMBean
 */
@ManagedBean(name = "languagetotalkdelete")
@ViewScoped
public class DeleteLanguageToTalkMBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote deleteLanguageToTalkRemote;

	/**
	 * Metode que es fa servir per eliminar un 'language To Talk'
	 * @return Nom del Facelet
	 * @throws Exception
	 */
	public String deleteLanguageToTalk(String nif, String language) throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		deleteLanguageToTalkRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		deleteLanguageToTalkRemote.deleteLanguageToTalk(nif, language);

		return "LanguagesToTalkListView";
	}
}
