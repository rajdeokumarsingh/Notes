/* $Header: $
 * Created on 21.11.2005
 * Copyright (c) 2005 Tribeo 
 */
package cz.sweb.pichlik.springtutorial.captcha;

/**
 * Controller for new comment, each form submit is protected by CAPTCHA.
 * 
 * @author 	 Roman Pichlík
 * @version  $Revision: 1.0 $ 
 * @since 	 1.0
 */
public class NewCommentForm extends ProtectedFormController {
	
	/**
     * @see org.springframework.web.servlet.mvc.SimpleFormController#doSubmitAction(java.lang.Object)
     */
	@Override
    protected void doSubmitAction(Object command) throws Exception {
		Comment comment = (Comment) command;
		//do something with new comment for example save to database.
    } 	
}
