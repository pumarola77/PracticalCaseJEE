package managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * ManagedBean ErrorBean
 * 
 * @author Grup 6
 * @version 1.0
 *
 */
@ManagedBean (name = "ErrorBean")
@RequestScoped
public class ErrorMbean {

	/**
	 * Getter StatusCode
	 * @return statuscode estat
	 */
	public String getStatusCode(){
		String val = String.valueOf((Integer)FacesContext.getCurrentInstance().getExternalContext().
				getRequestMap().get("javax.servlet.error.status_code"));
		return val;
	}

	/**
	 * Getter Message
	 * @return message missatge
	 */
	public String getMessage(){
		String val =  (String)FacesContext.getCurrentInstance().getExternalContext().
			getRequestMap().get("javax.servlet.error.message");
		return val;
	}

	/**
	 * Getter ExceptionType
	 * @return exceptiontype exceptiontype
	 */
	public String getExceptionType(){
		String val = FacesContext.getCurrentInstance().getExternalContext().
			getRequestMap().get("javax.servlet.error.exception_type").toString();
		return val;
	}

	/**
	 * Getter Exception
	 * @return exception exception
	 */
	public String getException(){
		String val =  (String)((Exception)FacesContext.getCurrentInstance().getExternalContext().
			getRequestMap().get("javax.servlet.error.exception")).toString();
		return val;
	}

	/**
	 * Getter RequestURI
	 * @return requestURI requestURI
	 */
	public String getRequestURI(){
		return (String)FacesContext.getCurrentInstance().getExternalContext().
			getRequestMap().get("javax.servlet.error.request_uri");
	}

	/**
	 * Getter ServletName
	 * @return servletname servletname
	 */
	public String getServletName(){
		return (String)FacesContext.getCurrentInstance().getExternalContext().
			getRequestMap().get("javax.servlet.error.servlet_name");
	}
}