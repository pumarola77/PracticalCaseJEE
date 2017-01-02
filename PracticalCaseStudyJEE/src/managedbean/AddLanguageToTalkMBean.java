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
 * 
 * @author Grup 6
 *
 */
@ManagedBean(name = "languagetotalkadd")
@ViewScoped
public class AddLanguageToTalkMBean implements Serializable{

	/**
	 * Obligatori perque la classe implementa Serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Aquest parametre serveix per controlar si el formulari de registre conte errors en les dades introduides
	 */
	private int success; 
	
	/**
	 * Aquest parametre serveix per mostrar un error a la propia pàgina del formulari
	 */
	protected String errorFormulari; 

	/**
	 * Llenguatge
	 */
	private String language;
	
	/**
	 * Nivell
	 */
	private String nivell;
	
	/**
	 * Despegable Llista llenguatges
	 */
	protected Collection<SelectItem> languagesList = new ArrayList<SelectItem>();
	
	/**
	 * Desplegable Llista nivells 
	 */
	protected Collection<SelectItem> nivellsList = new ArrayList<SelectItem>();
	
	/**
	 * EJB addLanguageToTalkRemote
	 */
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
	 * Getter valors del desplegable idiomes de la vista
	 * @return desplegable idioma
	 */
	public Collection<SelectItem> getLanguagesList() {		
		return languagesList;
	}
	
	/**
	 * Getter Valors del desplegable dels nivells de la vista
	 * @return
	 */
	public Collection<SelectItem> getNivellsList() {
		return nivellsList;
	}
	
	/**
	 * Assigna el valor del despegable Idioma
	 * @param languageChanged
	 */
	public void languageValueChanged(ValueChangeEvent languageChanged) 
	{		
		this.setLanguage(languageChanged.getNewValue().toString());
	}
		
	/**
	 * Assigna el valor del desplegable nivell
	 * @param nivellChanged
	 */
	public void nivellValueChanged(ValueChangeEvent nivellChanged) {
		this.setNivell(nivellChanged.getNewValue().toString());
	}
	
	/**
	 * Obte formulari
	 * @return
	 */
	public String getErrorFormulari(){
		return errorFormulari;
	}
	
	/**
	 * Assigna valor formulari
	 * @param errorFormulari
	 */
	public void setErrorFormulari (String errorFormulari){
		this.errorFormulari = errorFormulari;
	}

	/**
	 * Carrega els valors que mostra el desplegable llenguatge
	 * @throws Exception
	 */
	public void languagesList() throws Exception {
		languagesList.clear();
		
		
		// Recorre Classe Enum
		for(Language d: Language.values() ) {	
			
			SelectItem item = new SelectItem(d.name());			
			this.languagesList.add(item);
		}
	}

	/**
	 * Carrega els valors que mostra el desplegable nivell
	 * @throws Exception
	 */
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
	
	/**
	 * Getter Llenguatge
	 * @return llenguatge
	 */
	public String getLanguage() {		
		return this.language;
	}
	
	/**
	 * Setter llenguatge
	 * @param language
	 */
	public void setLanguage(String language) {		
		this.language = language;
	}

	/**
	 * Getter nivell
	 * @return nivell
	 */
	public String getNivell() {
		return nivell;
	}

	/**
	 * Setter nivell
	 * @param nivell
	 */
	public void setNivell(String nivell) {
		this.nivell = nivell;
	}
	
}
