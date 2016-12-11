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
	
	private int success; //Aquest parametre serveix per controlar si el formulari de registre conte errors en les dades introduides
	protected String errorFormulari; //Aquest parametre serveix per mostrar un error a la propia pàgina del formulari

	@EJB
	private UserFacadeRemote addTalkedLanguageRemote;

	public String getErrorFormulari(){
		return errorFormulari;
	}
	public void setErrorFormulari (String errorFormulari){
		this.errorFormulari = errorFormulari;
	}
	
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
			success = addTalkedLanguageRemote.addTalkedLanguage(nif, language, level, description);

			// Si el registre de l'usuari no es pot realitzar es perque el NIF amb l'idioma ja esta introduit a la base de dades, per tant es mostra error
			if (success==1) // El codi d'error 1 indica que ja existeix el nif amb l'idioma a la base de dades
			{
				errorFormulari = "ERROR: L'usuari amb nif: " + nif + " ja compta amb l'idioma " + language;
				return "addTalkedLanguageView"; //Es retorna el nom de la vista a la que volem que ens redirigim, en aquest cas la mateixa			
			}
			else // Si success es diferent de 1 vol dir que la operació s'ha dut a terme correctament
			{
				return "TalkedLanguagesListView"; //Si la introducció de l'usuari es correcta es retorna la vista LanguagesToTalkListView.xhtml per a que automaticament es redireccioni cap alla
			}
			
		}catch (PersistenceException e) {
			throw e;
		} 
	}
}
