package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import ejb.UserFacadeRemote;

/**
 * Managed Bean AddTalkedLanguageMBean
 */
@ManagedBean(name = "talkedlanguageadd")
@SessionScoped
public class AddTalkedLanguageMBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote addTalkedLanguageRemote;

	/**
	 * Metode que es fa servir per afegir un 'talked Language'
	 * @return Nom del Facelet
	 * @throws Exception
	 */
	public String addTalkedLanguage() throws Exception
	{
		try
		{
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
			String nif = request.getParameter("userForm:nif");
			String language = request.getParameter("userForm:language");
			String level = request.getParameter("userForm:level");
			String description = request.getParameter("userForm:description");

			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			addTalkedLanguageRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
			addTalkedLanguageRemote.addTalkedLanguage(nif, language, level, description);

			return "TalkedLanguagesListView";

		}catch (PersistenceException e) {
			throw e;
		} 
	}
}
