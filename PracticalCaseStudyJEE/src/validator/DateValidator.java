package validator;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("DateValidator")
public class DateValidator implements Validator {

	public DateValidator(){
		
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		String dateStr = value.toString();
		
		if(dateStr.isEmpty()){
			FacesMessage msg = 	new FacesMessage("Value failed.", "This Field is Empty.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		else if(!dateStr.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)")){
			FacesMessage msg = 	new FacesMessage("Value failed.", "Format erroni");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
