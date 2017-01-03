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
 * Managed Bean AddTalkedLanguageMBean
 * @author Grup 6
 * @version 1.0
 *
 */
@ManagedBean(name = "talkedlanguageadd")
@ViewScoped
public class AddTalkedLanguageMBean implements Serializable{

	/**
	 * Obligatori perque a casse implementa serializable
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
	 * Variable language
	 */
	private String language;
	
	/**
	 * Variable nivell
	 */
	private String nivell;
	
	/**
	 * Desplegable llista de llenguatges
	 */
	protected Collection<SelectItem> languagesList = new ArrayList<SelectItem>();
	
	/**
	 * Desplegable llista nivells
	 */
	protected Collection<SelectItem> nivellsList = new ArrayList<SelectItem>();
	
	/**
	 * EJB UserFacaeRemote
	 */
	@EJB
	private UserFacadeRemote addTalkedLanguageRemote;

	/**
	 * Constructor 
	 * Carrega valors
	 * @throws Exception alta llenguatge
	 */
	public AddTalkedLanguageMBean() throws Exception {
		this.languagesList();
		this.nivellsList();
	}
	
	/**
	 * Retorna els valors del desplegable llenguatge a la vista
	 * @return llista idiomes
	 */
	public Collection<SelectItem> getLanguagesList() {		
		return languagesList;
	}
	
	/**
	 * Retorna els valors del despegable dels nivell 
	 * @return llista de nivells
	 */
	public Collection<SelectItem> getNivellsList() {
		return nivellsList;
	}
	
	/**
	 * Assigna el valor del desplegable a la variable llenguatge
	 * @param languageChanged idioma
	 */
	public void languageValueChanged(ValueChangeEvent languageChanged) 
	{		
		this.setLanguage(languageChanged.getNewValue().toString());
	}
		
	/**
	 * Assigna el valor del desplegable a la variable nivell
	 * @param nivellChanged nivell
	 */
	public void nivellValueChanged(ValueChangeEvent nivellChanged) {
		this.setNivell(nivellChanged.getNewValue().toString());
	}
	
	/**
	 * Getter Obte missatge error del formulari
	 * @return missatge error Formulari
	 */
	public String getErrorFormulari(){
		return errorFormulari;
	}
	
	/**
	 * Setter Envia missatge error
	 * @param errorFormulari errorformulari
	 */
	public void setErrorFormulari (String errorFormulari){
		this.errorFormulari = errorFormulari;
	}
	
	/**
	 * Carrega els valors que mostrara el desplegable llenguatge
	 * @throws Exception incidencia al carregar desplegable
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
	 * Carrega els valors que mostrara el desplegable nivell
	 * @throws Exception incidencia al carregar desplegable
	 */
	public void nivellsList() throws Exception {
		nivellsList.clear();
		
		for(LevelLanguage d: LevelLanguage.values()) {
			SelectItem item = new SelectItem(d.name());
			this.nivellsList.add(item);
		}
	}
	
	/**
	 * Metode que es fa servir per afegir un 'talked Language'
	 * @return Nom del Facelet
	 * @throws Exception incidencia al afegir llenguatge
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
	
	/**
	 * Getter Language
	 * @return language
	 */
	public String getLanguage() {		
		return this.language;
	}
	
	/**
	 * Settere language
	 * @param language llenguatge
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
	 * @param nivell nivell
	 */
	public void setNivell(String nivell) {
		this.nivell = nivell;
	}
	
}
