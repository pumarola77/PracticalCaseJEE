package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.CatalogFacadeRemote;
import ejb.UserFacadeRemote;
import jpa.PetJPA;
import jpa.UserJPA;
import javax.annotation.PostConstruct;
@ManagedBean(name = "UpdateUser")
@SessionScoped
public class UpdatePersonalData implements Serializable {

private static final long serialVersionUID = 1L;

	protected String nif;
	protected String name;
	protected String surname;
	protected String phone;
	protected String password;
	protected String email;
	
	private boolean success; //Aquest parametre serveix per controlar si el formulari de registre conte errors en les dades introduides
	protected String errorFormulari; //Aquest parametre serveix per mostrar un error a la propia pàgina del formulari
	private UserJPA findUser; //Ens serveix per trobar l'user de la sessio
	@EJB
	private UserFacadeRemote userRemote;
	/*
	@EJB
	private UserFacade userLocalFacade;
	*/
	
	public UpdatePersonalData() throws Exception { 

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
	
	public void setFindUser() throws Exception {
		
		findUser = (UserJPA) userRemote.findUser(nif);

		setName(findUser.getName());
		setSurname(findUser.getSurname());
		setPhone(findUser.getPhone());
		setPassword(findUser.getPassword());
		setEmail(findUser.getEmail());
	}
	
	public String updateUsr(String nif, String name , String surname, String phone, String password , String email) throws Exception
	{
		try {
			
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			
			userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
			userRemote.updatePersonalData(nif, name, surname, phone, password, email);
			
		} catch (Exception e) {
			return "ErrorView";
		}
		
		return "Login.xhtml";
	}
	
}
