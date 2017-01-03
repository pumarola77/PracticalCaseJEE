package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("PhoneValidator")
public class PhoneValidator implements Validator {

	public PhoneValidator() {
		
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
	
		
		String phone  = object.toString();
		
		if (phone.matches(".*\\s+.*"))
		{
			FacesMessage msg = 	new FacesMessage("Validaci� del tel�fon err�nia.", "El tel�fon no pot contenir espais en blanc");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		if (!phone.matches("\\d+"))
		{
			FacesMessage msg = 	new FacesMessage("Validaci� del tel�fon err�nia.", "El tel�fon �nicament ha de contenir n�meros");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
					
	}
	
	

}
