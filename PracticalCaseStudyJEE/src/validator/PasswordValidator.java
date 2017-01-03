package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("PasswordValidator")
public class PasswordValidator implements Validator {

	
	public PasswordValidator() {
		
	}
	
	/**
	 * Metode per validar les dades d'entrada de les contrasenyes en registrar / actualitzar un usuari
	 * @param context Contexte del component a validar.
	 * @param component Component de la interficie a validar.
	 * @param value Objecte que conte el valor que s'ha de validar.
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		
		/*Es recupera el valor del camp password*/
		String passwd = value.toString();
		
		/*Es recupera el valor del camp password2 (el de confirmació)*/
		UIInput inputConfirmaPassword = (UIInput) component.getAttributes().get("confirmaPassword");
		String confirmaPassword = inputConfirmaPassword.getSubmittedValue().toString();
		
		
		/*Es comprova que els password no estiguin buits*/
		if (passwd == null || passwd.isEmpty() || confirmaPassword == null || confirmaPassword.isEmpty()){
			return;
		}
		
		/*Es comprova que el password tingui més de 8 caracters*/
		if ( passwd.trim().length() < 8 )	 	
		{
			FacesMessage msg =	new FacesMessage("Validació errònia", "Mínim 8 Caracters");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		/*Es comprova que els dos passwords siguin iguals*/
		if (!passwd.equals(confirmaPassword)){
			
			FacesMessage msg =	new FacesMessage("Els passwords no coincideixen");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
			
		}
		
	}

	
}
