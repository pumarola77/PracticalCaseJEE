package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;

/**
 * Managed Bean DeleteTalkedLanguageMBean
 */
@ManagedBean(name = "talkedlanguagedelete")
@ViewScoped
public class DeleteTalkedLanguageMBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote deleteTalkedLanguageRemote;

	/**
	 * Metode que es fa servir per eliminar un 'talked Language'
	 * @return Nom del Facelet
	 * @throws Exception
	 */
	public String deleteTalkedLanguage(String nif, String language) throws Exception
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		deleteTalkedLanguageRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		deleteTalkedLanguageRemote.deleteTalkedLanguage(nif, language);

		return "TalkedLanguagesListView";
	}
}
