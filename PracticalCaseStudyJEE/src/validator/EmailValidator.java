package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validacio Email
 * @author Grup 6
 * @version 1.0
 *
 */
@FacesValidator("EmailValidator")
public class EmailValidator implements Validator {

	/**
	 * EMAIL_PATTERN Regex
	 */
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";

	/**
	 * Patter
	 */
	private Pattern pattern;
	
	/**
	 * matcher
	 */
	private Matcher matcher;

	/**
	 * Constructor
	 */
	public EmailValidator(){
		  pattern = Pattern.compile(EMAIL_PATTERN);
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
		
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){

			FacesMessage msg =
				new FacesMessage("Validació errònia",
						"Email amb format incorrecte");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
	
}
