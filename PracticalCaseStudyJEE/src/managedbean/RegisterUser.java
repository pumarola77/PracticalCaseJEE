
package managedbean;
import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;
//import ejb.UserFacade;

/**
 * ManagedBean adduser
 * 
 * @author Bazinga
 * @since 1.0
 */
@ManagedBean(name = "AddUser")
@ViewScoped
public class RegisterUser implements Serializable{

	/**
	 * Obligatori perque la classe es serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * identificador nif
	 */
	protected String nif;
	
	/**
	 * nom
	 */
	protected String name;
	
	/**
	 * surname
	 */
	protected String surname;
	
	/**
	 * phone
	 */
	protected String phone;
	
	/**
	 * password
	 */
	protected String password;
	
	/**
	 * email
	 */
	protected String email;
	
	/**
	 * Aquest parametre serveix per controlar si el formulari de registre conte errors en les dades introduides
	 */
	private int success; 
	
	/**
	 * Aquest parametre serveix per mostrar un error a la propia pàgina del formulari
	 */
	protected String errorFormulari; 
	
	/**
	 * EJB UserFacadeRemote
	 */
	@EJB
	private UserFacadeRemote userRemote;
	/*
	@EJB
	private UserFacade userLocalFacade;
	*/
	
	/**
	 * Constructor
	 */
	public RegisterUser()
	{
		nif = "";
		name = "";
		surname = "";
		phone = "";
		password = "";
		email = "";
		errorFormulari="";
		
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == true) {
			this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());
		}
	}
	
	/*Definim els getters i setters per a que desde les pagines JSP es pugui accedir als atributs idioma i nivell*/

	/**
	 * Getter nif
	 * @return nif
	 */
	public String getNif()
	{
		return nif;
	}
	
	/**
	 * Settere nif
	 * @param nif
	 */
	public void setNif(String nif)
	{
		this.nif=nif;		
	}
	
	/**
	 * Getter name
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Setter name
	 * @param name
	 */
	public void setName(String name)
	{
		this.name=name;
	}
	
	/**
	 * Getter surname
	 * @return surname
	 */
	public String getSurname()
	{
		return surname;
	}
	
	/**
	 * Setter surname
	 * @param surname
	 */
	public void setSurname(String surname)
	{
		this.surname=surname;
	}
	
	/**
	 * Getter phone
	 * @return phone
	 */
	public String getPhone()
	{
		return phone;
	}
	
	/**
	 * Setter phone
	 * @param phone
	 */
	public void setPhone(String phone)
	{
		this.phone=phone;
	}
	
	/**
	 * Getter password
	 * @return password
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * Setter passowrd
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password=password;
	}
	
	/**
	 * Getter email
	 * @return email
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * Setter email
	 * @param email
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * Getter missatge formulari
	 * @return missatge formulari
	 */
	public String getErrorFormulari(){
		
		return errorFormulari;
	}
	
	/**
	 * Setter missatge Formulari
	 * @param errorFormulari
	 */
	public void setErrorFormulari (String errorFormulari){
		
		this.errorFormulari = errorFormulari;
	}
	
	//Retorna la vista principal
	/**
	 * Retorna vista principal
	 * @return
	 */
	public String mainWindow() {
		return "RegisterUserView";
	}
	
	/*Metode per registrar un usuari al sistema*/
	/**
	 * Alta usuari
	 * 
	 * @param nif identificador usuari
	 * @param name nom
	 * @param surname surname
	 * @param phone phone
	 * @param password password
	 * @param email email
	 * @return vista 
	 * @throws Exception
	 */
	public String addUsr(String nif, String name, String surname, String phone, String password, String email) throws Exception	
	{		
		/* DESACTIVAT ELS VALORS ES PASSEN DES DEL FACELET -> VISTA
		//Tenim que cridar als setters aixi perque sino al fer click al boto de la pagina JSP no s'actualitzen els valors d'aquest objecte
		setNif(nif);
		setName(name);
		setSurname(surname);
		setPhone(phone);
		setPassword(password);
		setEmail(email);
		//userLocalFacade.registerUser(getNif(), getName(), getSurname(), getPhone(), getPassword(), getEmail());
		*/
		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		//success = userRemote.registerUser(getNif(), getName(), getSurname(), getPhone(), getPassword(), getEmail());
		success = userRemote.registerUser(nif, name, surname, phone, password, email);
		
		/*Si el registre de l'usuari no es pot realitzar es perque el DNI o l'email ja esta introduit a la base de dades, per tant es mostra error*/
		if (success==1) /*El codi d'error 1 indica que el dni ja existeix a la base de dades*/
		{
			errorFormulari = "ERROR: L'usuari amb nif: "+getNif()+" ja existeix al sistema";
			return "RegisterUserView.xhtml"; //Es retorna el nom de la vista a la que volem que ens redirigim, en aquest cas la mateixa			
		}
		else if (success==2)/*El codi d'error 2 indica que el email ja existeix a la base de dades*/
		{	 
			errorFormulari = "ERROR: L'usuari amb correu "+getEmail()+" ja existeix al sistema";
			return "RegisterUserView.xhtml"; //Es retorna el nom de la vista a la que volem que ens redirigim, en aquest cas la mateixa
		}
		else /*Si success es diferent de 1 o 2 vol dir que la operació s'ha dut a terme correctament*/
		{
			return "Login.xhtml"; //Si la introducció de l'usuari es correcta es retorna la vista Login.xhtml per a que automaticament es redireccioni cap alla
			
		}
	}
}
