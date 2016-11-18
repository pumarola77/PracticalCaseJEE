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
	
	/*Metode per registrar un usuari al sistema*/
	public void addUsr() throws Exception
	{
		//Tenim que cridar als setters aixi perque sino al fer click al boto de la pagina JSP no s'actualitzen els valors d'aquest objecte
		setNif(nif);
		setName(name);
		setSurname(surname);
		setPhone(phone);
		setPassword(password);
		setEmail(email);
		
		//userLocalFacade.registerUser(getNif(), getName(), getSurname(), getPhone(), getPassword(), getEmail());
		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
		userRemote.registerUser(getNif(), getName(), getSurname(), getPhone(), getPassword(), getEmail());
	}
}
