package managedbean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;

/**
 * ManagedBean LoginBean
 * @author Bazinga
 * @since 1.0
 *
 */
@ManagedBean(name = "LoginBean")
@RequestScoped
public class Login {
	
	/**
	 * email
	 */
	protected String email;
	
	/**
	 * Password
	 */
	protected String password;
	
	//Serveix per controlar si l'usuari el password son correctes.
	/**
	 * Identificador nif
	 */
	private String nif;
	
	/**
	 * Missatge error
	 */
	protected String errorPassword; //Serveix per mostrar error en pantalla
	
	/**
	 * EJB UserFacadeRemote
	 */
	@EJB
	private UserFacadeRemote userRemote;
	

	/**
	 * Constructor
	 */
	public Login() {	
		//Refresca les variables de Sessio
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap();
	}
	
	
	/* Definicio dels getter i Setters */
	/**
	 * Getter Email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter email
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter Password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter password
	 * @param password password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Getter errorPassword
	 * @return errorPassword 
	 */
	public String getErrorPassword() {
		return errorPassword;
	}
	
	/**
	 * Setter errorPassword
	 * @param errorPassword errorPassword
	 */
	public void setErrorPassword(String errorPassword) {
		this.errorPassword = errorPassword;
	}
	
	// Get / Setter del Nif
	/**
	 * Getter nif
	 * @return nif 
	 */
	public String getNif() {
		return nif;
	}
	
	/**
	 * Setter nif
	 * @param nif nif
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}	
	
	/* Metode per Verificar el Login Usuari */
	/**
	 * Validar usuari
	 * @param email email
	 * @param pwd password
	 * @return vista 
	 */
	public String validarUsuari(String email, String pwd) {
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			FacesContext context = FacesContext.getCurrentInstance();
				
			/* Es controla a la vista */
			/*
			if ( email.isEmpty()) {
				errorPassword = "Falta Indicar Codi Acces";
				return "Login";
			}
			
			if ( pwd.isEmpty()) {
				errorPassword = "Falta Indicar Clau Acces";
				return "Login";				
			}
			*/
			
			userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
			nif = userRemote.login(email, pwd);
						
			//haure de capturar el nif per poder-lo passar com a variable de sessio
									
			if ( !nif.equals("NOVALID") ) {
				
				//Buscar el nif corresponent al nif del registre seleccionat
				
				context.getExternalContext().getSessionMap().put("nif", nif);
				this.setNif(nif);
				errorPassword = "";
				
				// Hauria de Retornar la vista per entrar al menu
				return "HomeView";
			} else {
				context.getExternalContext().invalidateSession();
				FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap();
				errorPassword = "Usuari no existeix o password incorrecte";
				return "Login"; 				
			}
			
		} catch(Exception e) {
			return "ErrorView";
		}

	}
	
	/**
	 * Sortida aplicacio
	 * @return vista
	 */
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap();
		return "Login";
	}
	
}
