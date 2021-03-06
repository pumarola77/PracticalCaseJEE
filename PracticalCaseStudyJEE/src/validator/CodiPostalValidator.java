package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validacions del codi postal
 * 
 * @author Grup 6
 * @version 1.0
 *
 */
@FacesValidator("CodiPostalValidator")
public class CodiPostalValidator implements Validator {

	/**
	 * Constructor
	 */
	public CodiPostalValidator() {
		
	}
	
	/**
	 * Valida Entrada del camp 
	 * 
	 * @param arg0 FacesContext 
	 * @param component UIComponent
	 * @param object Object
	 */
	@Override
	public void validate(FacesContext arg0, UIComponent component, Object object) throws ValidatorException {
		// TODO Auto-generated method stub
		
		String codiPostal  = object.toString();
		
		if (codiPostal.matches(".*\\s+.*"))
		{
			FacesMessage msg = 	new FacesMessage("Validaci� del tel�fon err�nia.", "El codipostal no pot contenir espais en blanc");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		if (!codiPostal.matches("\\d+"))
		{
			FacesMessage msg = 	new FacesMessage("Validaci� del tel�fon err�nia.", "�nicament ha de contenir n�meros");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

		
	}
	
	

}
