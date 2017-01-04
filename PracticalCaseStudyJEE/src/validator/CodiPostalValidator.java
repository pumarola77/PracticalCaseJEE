package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("CodiPostalValidator")
public class CodiPostalValidator implements Validator {

	public CodiPostalValidator() {
		
	}
	
	@Override
	public void validate(FacesContext arg0, UIComponent component, Object object) throws ValidatorException {
		// TODO Auto-generated method stub
		
		String codiPostal  = object.toString();
		
		if (codiPostal.matches(".*\\s+.*"))
		{
			FacesMessage msg = 	new FacesMessage("Validació del telèfon errònia.", "El codipostal no pot contenir espais en blanc");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		if (!codiPostal.matches("\\d+"))
		{
			FacesMessage msg = 	new FacesMessage("Validació del telèfon errònia.", "Únicament ha de contenir números");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

		
	}
	
	

}
