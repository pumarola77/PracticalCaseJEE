package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("PasswordValidator")
public class PasswordValidator implements Validator {

	
	public PasswordValidator() {
		
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		
		String passwd = value.toString();
		
		if ( passwd.isEmpty() || passwd.trim().length() == 0 || 
			 passwd.trim().length() < 8 )	 	
		{
			FacesMessage msg =	new FacesMessage("Password validation failed.", "Mínim 8 Caracters");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
	}

	
}
