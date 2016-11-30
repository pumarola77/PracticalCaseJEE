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
 * Managed Bean AddLanguageToTalkMBean
 */
@ManagedBean(name = "languagetotalkadd")
@SessionScoped
public class AddLanguageToTalkMBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote addLanguageToTalkRemote;

	/**
	 * Metode que es fa servir per afegir un 'Language To Talk'
	 * @return Nom del Facelet
	 * @throws Exception
	 */
	public String addLanguageToTalk() throws Exception
	{
		try
		{
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
			String nif = request.getParameter("userForm:nif");
			String language = request.getParameter("userForm:language");
			String level = request.getParameter("userForm:level");
			String description = request.getParameter("userForm:description");
			boolean acceptPay =request.getParameter("userForm:acceptPay") != null;

			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			addLanguageToTalkRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
			addLanguageToTalkRemote.addLanguageToTalk(nif, language, level, description, acceptPay);

			return "LanguagesToTalkListView";

		}catch (PersistenceException e) {
			throw e;
		} 
	}
}
