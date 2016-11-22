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
		
	/* Metode per Verificar el Login Usuari */
	public String login(String email, String pwd) {
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
						
			userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
			success = userRemote.login(email, pwd);
						
			if ( success ) {
				// Hauria Entrar a un Menu Per seleccionar les 
				// opcions disponibles del programa.
				return "Login";
			} else {
				return "RegisterView";
			}
			
		} catch(Exception e) {
			return "ErrorView";
		}

	}
	
}
