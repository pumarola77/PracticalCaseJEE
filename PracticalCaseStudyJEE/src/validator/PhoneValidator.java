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
		// TODO Auto-generated method stub
		
		String phone  = object.toString();
		
		for ( int i=0; i < phone.trim().length(); i++ ) {
			
			if ( Character.isLetter(phone.charAt(i)) ) {
				FacesMessage msg = 	new FacesMessage("Phone validation failed.", "Invalid Phone format.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);			
			}
			
		}
					
	}
	
	

}
