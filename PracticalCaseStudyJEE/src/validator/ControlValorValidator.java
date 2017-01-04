package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validacions de camp en blanc
 * 
 * @author Grup 6
 * @version 1.0
 *
 */
@FacesValidator("ControlValorValidator")
public class ControlValorValidator implements Validator {

	/**
	 * Constructor
	 */
	public ControlValorValidator() {
		
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
			
		if ( valor.isEmpty() || valor.trim().length() == 0 ) {
			FacesMessage msg = 	new FacesMessage("Value failed.", "This Field is Empty.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
	}
	
	

}
