package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validacio NIF
 * @author Grup 6
 * @version 1.0
 */
@FacesValidator("NIFValidator")
public class NIFValidator implements Validator{
	
	/**
	 * Constructor
	 */
	public NIFValidator() {
		
	}
	
	/**
	 * Valida Entrada del camp 
	 * 
	 * @param context FacesContext 
	 * @param component UIComponent
	 * @param object Object
	 */

	@Override
	public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
		// TODO Auto-generated method stub
				
		// Recollir nif
		String nif = object.toString();
				
		//Array amb les lletres posssibles del dni a la seva posicio
		char[] lletraDni = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D',  'X',  'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E' };
		
        String num= "";
        int ind = 0;  
                
        if ( nif.trim().length() == 8 ) {
        	nif = '0' + nif;
        }
                
        // Longitud minima 9 caracters
        if ( nif.trim().length() != 9 ) {
			FacesMessage msg = 	new FacesMessage("Validaci� err�nia", "Format del DNI incorrecte. Longitud err�nia");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);        	
        }
        
        
        //Comproba que el 9 digit  sigui una lletra
        if ( !Character.isLetter(nif.charAt(8))) {        	
			FacesMessage msg = 	new FacesMessage("Validaci� err�nia", "L'ultim car�cter no es una lletra");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
        }
                
        //Comproba que els 8 primers digits siguin numeros
        for (int i=0; i<8; i++) {
        	
        	if ( !Character.isDigit(nif.charAt(i)) ) {
    			FacesMessage msg = 	new FacesMessage("Validaci� err�nia", "El camp cont� lletres o car�cters");
    			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    			throw new ValidatorException(msg);
        	}
        	
        	num += nif.charAt(i);
        	
        }
                
        ind = Integer.parseInt(num);
        
        //Calcula la posicio de la lletra en el array que corresponent al dni entrat.
        ind %= 23;
                
        //Verificacioo que la lletra del dni correspongui amb l'array
        if ((Character.toUpperCase(nif.charAt(8))) != lletraDni[ind]){
			FacesMessage msg = 	new FacesMessage("Validaci� err�nia", "La lletra del DNI no es valida");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
        }  
        		
	}

	
	
}
