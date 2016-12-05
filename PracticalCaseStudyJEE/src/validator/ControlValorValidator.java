package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("ControlValorValidator")
public class ControlValorValidator implements Validator {

	public ControlValorValidator() {
		
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
		// TODO Auto-generated method stub
		
		String valor = object.toString();
		
		if ( valor.isEmpty() || valor.trim().length() == 0 ) {
			FacesMessage msg = 	new FacesMessage("Value failed.", "This Field is Empty.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
	}
	
	

}
