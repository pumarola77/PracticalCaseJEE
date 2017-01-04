package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validacio nom / cognom
 * @author Grup 6
 * @version 1.0
 *
 */
@FacesValidator("NameSurnameValidator")
public class NameSurnameValidator implements Validator {

	/**
	 * Constructor
	 */
	public NameSurnameValidator() {
		
	}
	
	/**
	 * Valida Entrada del camp 
	 * 
	 * @param context FacesContext 
	 * @param component UIComponent
	 * @param object Object
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
		
		String valor = object.toString();
			
		if (valor.matches("^\\s.*"))
		{
			FacesMessage msg = 	new FacesMessage("Validació errònia", "No pot haver espais en blanc al principi");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		if (valor.matches(".*\\s$"))
		{
			FacesMessage msg = 	new FacesMessage("Validació errònia", "No pot haver espais en blanc al final");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		if (valor.matches(".*[ ]{2,}.*"))
		{
			FacesMessage msg = 	new FacesMessage("Validació errònia", "No pot haver més d'un espai en blanc entre paraules");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		if (!valor.matches("[a-zA-Z ]+"))
		{
			FacesMessage msg = 	new FacesMessage("Validació errònia", "Únicament es poden utilitzar caràcters alfabètics");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
	}
	
	

}
