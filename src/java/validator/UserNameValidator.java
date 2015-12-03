/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
/**
 *
 * @author Jon
 */

@FacesValidator(value="userNameValidator")
public class UserNameValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String userName = ((String)value).trim().toLowerCase();
        System.out.println("@@@@@ " + userName);
        System.out.println(!userName.matches("^[a-z0-9_-]{3,15}$"));
        HtmlInputText htmlInputText = (HtmlInputText)component;
        //check if the username contains only letters and/or numbers
        if(!userName.matches("^[a-z0-9_-]{3,15}$"))
        {
            FacesMessage facesMsg = new FacesMessage(htmlInputText.getLabel()+": username may not contain spaces or special characters and must be between 3 and 15 characters long.");
            throw new ValidatorException(facesMsg);
        }
    }

    
    
}
