package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;
import jpa.UserJPA;

/**
 * ManagedBean updateuser
 * 
 * @author Bazinga
 * @version 1.0
 *
 */
@ManagedBean(name = "UpdateUser")
@ViewScoped
public class UpdatePersonalData implements Serializable {

	/**
	 * Obligatori perque implementa serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * nif
	 */
	protected String nif;
	
	/**
	 * name
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
	 * emailUser
	 */
	protected String emailUser;
	
	/**
	 * success
	 */
	private boolean success;
	
	/**
	 * Aquest parametre serveix per mostrar un error a la propia pàgina del formulari
	 */
	protected String errorFormulari; 
	
	/**
	 * Ens serveix per trobar l'user de la sessio
	 */
	private UserJPA findUser; 
	
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
	 * @throws Exception actualitzar dades
	 */
	public UpdatePersonalData() throws Exception { 

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		
		if ( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == true) {
			this.setNif(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nif").toString());
									
			//Actualita getters / setters
			setFindUser();
		}
	}
	
	/**
	 * retorna vista principal
	 * @return vista
	 */
	public String Home() {
		return "HomeView";
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
	 * Setter nif
	 * @param nif identificador usuari
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
	 * @param name identificador nom
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
	 * Seter Surname
	 * @param surname cognom
	 */
	public void setSurname(String surname)
	{
		this.surname=surname;
	}
	
	/**
	 * Getter Phone
	 * @return phone
	 */
	public String getPhone()
	{
		return phone;
	}
	
	/**
	 * Setter phone
	 * @param phone telefon
	 */
	public void setPhone(String phone)
	{
		this.phone=phone;
	}
	
	/**
	 * Getter Password
	 * @return password
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * Setter password
	 * @param password password
	 */
	public void setPassword(String password)
	{
		this.password=password;
	}
	
	/**
	 * Getter Email
	 * @return email
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * Setter email
	 * @param email email
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * Getter emailUser
	 * @return emailUser
	 */
	public String getEmailUser(){
		return emailUser;
	}
	
	/**
	 * Setter emailUser
	 * @param emailUser emailusuari
	 */
	public void setEmailUser(String emailUser){
		this.emailUser = emailUser;
	}
	
	/**
	 * Getter ErrorFormulari
	 * @return errorFormulari
	 */
	public String getErrorFormulari(){
		
		return errorFormulari;
	}
	
	/**
	 * Setter Errorformulari
	 * @param errorFormulari errorFormulari
	 */
	public void setErrorFormulari (String errorFormulari){
		
		this.errorFormulari = errorFormulari;
	}
	
	/**
	 * cerca usuari
	 * @throws Exception incidencia al buscar usuari
	 */
	public void setFindUser() throws Exception {
		
		findUser = (UserJPA) userRemote.findUser(this.nif);

		this.setName(findUser.getName());
		this.setSurname(findUser.getSurname());
		this.setPhone(findUser.getPhone());
		this.setPassword(findUser.getPassword());
		this.setEmail(findUser.getEmail());
		this.setEmailUser(findUser.getEmail());
	}
	
	/**
	 * Actualitza usuari
	 * 
	 * @param nif nif
	 * @param name name
	 * @param surname surname
	 * @param phone phone
	 * @param password password
	 * @param email email
	 * @return vista
	 * @throws Exception actualitzar dades usuari
	 */
	public String updateUsr(String nif, String name , String surname, String phone, String password , String email) throws Exception
	{

			success = userRemote.updatePersonalData(nif, name, surname, phone, password, email);
	
			if(success == false && email.compareTo(this.getEmailUser()) != 0)
			{
				errorFormulari = "ERROR: L'email "+getEmail()+" ja existeix al sistema";
				return "RegisterUserView";
			}
			else{
				return "HomeView";
			}
	}
	
}
