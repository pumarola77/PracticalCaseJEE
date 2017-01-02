package managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import ejb.UserFacadeRemote;
import jpa.Language;
import jpa.LevelLanguage;

/**
 * Managed Bean AddLanguageToTalkMBean
 * 
 * @author Bazinga
 * @since 1.0
 */
@ManagedBean(name = "authorized")
@ViewScoped
public class NotAuthorizedMbean implements Serializable{

	/**
	 * Obligatori implementa la classe serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public NotAuthorizedMbean() {}
	
	/**
	 * Si no s'ha idenficat usuari no autoritza acces pàgina
	 * @throws IOException
	 */
	public void authorized() throws IOException
	{
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nif") == false)
		{
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("NotAuthorizedView.xhtml");		
		}
		
	}
}
