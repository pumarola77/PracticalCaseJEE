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

/**
 * Validacio hores
 * 
 * @author Grup 6
 * @version 1.0
 *
 */
@FacesValidator("TimeValidator")
public class TimeValidator implements Validator {

	/**
	 * Constructor
	 */
	public TimeValidator(){
		
	}
	
	/**
	 * Valida Entrada del camp 
	 * 
	 * @param context FacesContext 
	 * @param component UIComponent
	 * @param value Object
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		String timeStr = value.toString();
		
		if(timeStr.isEmpty()){
			FacesMessage msg = 	new FacesMessage("Validació errònia", "El camp està buit");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		else if(!timeStr.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")){
			FacesMessage msg = 	new FacesMessage("Validació errònia", "Format erroni");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
