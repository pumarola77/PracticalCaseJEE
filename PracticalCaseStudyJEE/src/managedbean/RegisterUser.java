
package managedbean;
import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;
//import ejb.UserFacade;

@ManagedBean(name = "AddUser")
@SessionScoped
public class RegisterUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String nif;
	protected String name;
	protected String surname;
	protected String phone;
	protected String password;
	protected String email;
	
	private int success; //Aquest parametre serveix per controlar si el formulari de registre conte errors en les dades introduides
	protected String errorFormulari; //Aquest parametre serveix per mostrar un error a la propia pàgina del formulari
	
	@EJB
	private UserFacadeRemote userRemote;
	/*
	@EJB
	private UserFacade userLocalFacade;
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
	}
	
	/*Definim els getters i setters per a que desde les pagines JSP es pugui accedir als atributs idioma i nivell*/

	public String getNif()
	{
		return nif;
	}
	
	public void setNif(String nif)
	{
		this.nif=nif;		
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public String getSurname()
	{
		return surname;
	}
	
	public void setSurname(String surname)
	{
		this.surname=surname;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public void setPhone(String phone)
	{
		this.phone=phone;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password=password;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getErrorFormulari(){
		
		return errorFormulari;
	}
	
	public void setErrorFormulari (String errorFormulari){
		
		this.errorFormulari = errorFormulari;
	}
	
	//Retorna la vista principal
	public String mainWindow() {
		return "RegisterUserView";
	}
	
	/*Metode per registrar un usuari al sistema*/
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
