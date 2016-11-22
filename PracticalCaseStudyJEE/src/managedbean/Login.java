package managedbean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;

@ManagedBean(name = "Login")
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected String email;
	protected String password;
	
	//Serveix per controlar si l'usuari el password son correctes.
	private boolean success;
	protected String errorPassword; //Serveix per mostrar error en pantalla
	
	@EJB
	private UserFacadeRemote userRemote;
	
	//Constructor MBean
	//public Login() {
		
	//}
	
	
	/* Definicio dels getter i Setters */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getErrorPassword() {
		return errorPassword;
	}
	
	public void setErrorPassword(String errorPassword) {
		this.errorPassword = errorPassword;
	}
	
		
	/* Metode per Verificar el Login Usuari */
	public String login(String email, String pwd) {
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
						
			if ( email.isEmpty()) {
				errorPassword = "Falta Indicar Codi Acces";
				return "Login";
			}
			
			if ( pwd.isEmpty()) {
				errorPassword = "Falta Indicar Clau Acces";
				return "Login";				
			}
			
			userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
			success = userRemote.login(email, pwd);
						
			if ( success ) {
				// Hauria Entrar a un Menu Per seleccionar les 
				// opcions disponibles del programa.
				errorPassword = "";
				return "Login";
			} else {
				
				errorPassword = "Usuari No existeix o passowrd incorrecte";
				return "Login"; 				
			}
			
		} catch(Exception e) {
			return "ErrorView";
		}

	}
	
}
