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
		/*
		if ( valor.isEmpty() || valor.trim().length() == 0 ) {
			FacesMessage msg = 	new FacesMessage("Value failed.", "This Field is Empty.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}*/
		
	}
	
	

}
