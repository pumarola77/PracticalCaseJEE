package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import ejb.UserFacadeRemote;
import jpa.Language;
import jpa.LevelLanguage;

/**
 * Managed Bean AddLanguageToTalkMBean
 */
@ManagedBean(name = "languagetotalkadd")
@ViewScoped
public class AddLanguageToTalkMBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private int success; //Aquest parametre serveix per controlar si el formulari de registre conte errors en les dades introduides
	protected String errorFormulari; //Aquest parametre serveix per mostrar un error a la propia pàgina del formulari

	private String language;
	private String nivell;
	
	// Desplegable llista de llenguatges
	protected Collection<SelectItem> languagesList = new ArrayList<SelectItem>();
	protected Collection<SelectItem> nivellsList = new ArrayList<SelectItem>();
	
	@EJB
	private UserFacadeRemote addLanguageToTalkRemote;

	
	/**
	 * Constructor 
	 * Carrega valors
	 * @throws Exception 
	 */
	public AddLanguageToTalkMBean() throws Exception {
		this.languagesList();
		this.nivellsList();
	}
	
	/**
	 * Retorna els valors del desplegable a la vista
	 * @return desplegable idioma
	 */
	public Collection<SelectItem> getLanguagesList() {		
		return languagesList;
	}
	
	public Collection<SelectItem> getNivellsList() {
		return nivellsList;
	}
	
	public void languageValueChanged(ValueChangeEvent languageChanged) 
	{		
		this.setLanguage(languageChanged.getNewValue().toString());
	}
		
	public void nivellValueChanged(ValueChangeEvent nivellChanged) {
		this.setNivell(nivellChanged.getNewValue().toString());
	}
	
	public String getErrorFormulari(){
		return errorFormulari;
	}
	public void setErrorFormulari (String errorFormulari){
		this.errorFormulari = errorFormulari;
	}

	/**
	 * Carrega els valors que mostrara el desplegable
	 */
	public void languagesList() throws Exception {
		languagesList.clear();
		
		
		// Recorre Classe Enum
		for(Language d: Language.values() ) {	
			
			SelectItem item = new SelectItem(d.name());			
			this.languagesList.add(item);
		}
	}
	
	public void nivellsList() throws Exception {
		nivellsList.clear();
		
		for(LevelLanguage d: LevelLanguage.values()) {
			SelectItem item = new SelectItem(d.name());
			this.nivellsList.add(item);
		}
	}
	
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
			success = addLanguageToTalkRemote.addLanguageToTalk(nif, language, level, description, acceptPay);

			// Si el registre de l'usuari no es pot realitzar es perque el NIF amb l'idioma ja esta introduit a la base de dades, per tant es mostra error
			if (success==1) // El codi d'error 1 indica que ja existeix el nif amb l'idioma a la base de dades
			{
				errorFormulari = "ERROR: L'usuari amb nif: " + nif + " ja compta amb l'idioma " + language;
				return "addLanguageToTalkView"; //Es retorna el nom de la vista a la que volem que ens redirigim, en aquest cas la mateixa			
			}
			else // Si success es diferent de 1 vol dir que la operació s'ha dut a terme correctament
			{
				return "LanguagesToTalkListView"; //Si la introducció de l'usuari es correcta es retorna la vista LanguagesToTalkListView.xhtml per a que automaticament es redireccioni cap alla
			}

		}catch (PersistenceException e) {
			throw e;
		} 
	}
	
	public String getLanguage() {		
		return this.language;
	}
	
	public void setLanguage(String language) {		
		this.language = language;
	}

	public String getNivell() {
		return nivell;
	}

	public void setNivell(String nivell) {
		this.nivell = nivell;
	}
	
}
