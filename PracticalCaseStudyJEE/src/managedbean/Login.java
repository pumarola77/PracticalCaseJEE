package managedbean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;

@ManagedBean(name = "Login")
@RequestScoped
public class Login {
	
	protected String email;
	protected String password;
	
	//Serveix per controlar si l'usuari el password son correctes.
	private String nif;
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
			FacesContext context = FacesContext.getCurrentInstance();
						
			if ( email.isEmpty()) {
				errorPassword = "Falta Indicar Codi Acces";
				return "Login";
			}
			
			if ( pwd.isEmpty()) {
				errorPassword = "Falta Indicar Clau Acces";
				return "Login";				
			}
			
			userRemote = (UserFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/UserFacadeBean!ejb.UserFacadeRemote");
			nif = userRemote.login(email, pwd);
						
			//haure de capturar el nif per poder-lo passar com a variable de sessio
									
			if ( !nif.equals("NOVALID") ) {
				
				//Buscar el nif corresponent al nif del registre seleccionat
				
				context.getExternalContext().getSessionMap().put("nif", nif);
				errorPassword = "";
				
				// Hauria de Retornar la vista per entrar al menu
				return "Login";
			} else {
				context.getExternalContext().invalidateSession();
				errorPassword = "Usuari No existeix o passowrd incorrecte";
				return "Login"; 				
			}
			
		} catch(Exception e) {
			return "ErrorView";
		}

	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "Login";
	}
	
}
