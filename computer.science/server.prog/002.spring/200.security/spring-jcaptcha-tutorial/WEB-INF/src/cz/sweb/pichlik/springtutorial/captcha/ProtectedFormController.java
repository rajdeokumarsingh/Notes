/* $Header: $
 * Created on 21.11.2005
 * Copyright (c) 2005 Tribeo 
 */
package cz.sweb.pichlik.springtutorial.captcha;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * Convenient superclass for form controller implementations which need
 * CAPTCHA support. 
 * 
 * @author 	 Roman Pichlík
 * @version  $Revision: 1.0 $ 
 * @since 	 1.0
 */
public class ProtectedFormController extends SimpleFormController {
	/**
	 * Default paramter name for CAPTCHA response in <code>{@link HttpServletRequest}</code>
	 */
	private static final String DEFAULT_CAPTCHA_RESPONSE_PARAMETER_NAME = "j_captcha_response";
	
	protected ImageCaptchaService captchaService;
	protected String captchaResponseParameterName = DEFAULT_CAPTCHA_RESPONSE_PARAMETER_NAME;
		
	/**
	 * Delegates request to CAPTCHA validation, subclasses which overrides this 
	 * method must manually call <code>{@link #validateCaptcha(HttpServletRequest, BindException)}</code>
	 * or explicitly call super method.
	 * 
	 * @see #validateCaptcha(HttpServletRequest, BindException)
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {		
		validateCaptcha(request, errors);
	}
	
	/**
	 * Validate CAPTCHA response, if response isn`t valid creates new error object 
	 * and put him to errors holder.
	 * 
	 * @param request current servlet request
	 * @param errors errors holder
	 */
	protected void validateCaptcha(HttpServletRequest request, BindException errors){
		boolean isResponseCorrect = false;
		
        //remenber that we need an id to validate!
        String captchaId = request.getSession().getId();
        //retrieve the response
        String response = request.getParameter(captchaResponseParameterName);
        //validate response
        try {			
			if(response != null){
				isResponseCorrect =
					captchaService.validateResponseForID(captchaId, response);
			}
		} catch (CaptchaServiceException e) {
		    //should not happen, may be thrown if the id is not valid			
		}
		
		if(!isResponseCorrect){
			//prepare object error, captcha response isn`t valid
	        String objectName = "Captcha";
			String[] codes = {"invalid"};
			Object[] arguments = {};
			String defaultMessage = "Wrong cotrol text!";
			ObjectError oe = new ObjectError(objectName, codes, arguments, defaultMessage);
			errors.addError(oe);
		}         
	}

	/**
	 * Set captcha service
	 * @param captchaService the captchaService to set.
	 */
	public void setCaptchaService(ImageCaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	/**
	 * Set paramter name for CAPTCHA response in <code>{@link HttpServletRequest}</code>
	 * @param captchaResponseParameterName the captchaResponseParameterName to set.
	 */
	public void setCaptchaResponseParameterName(String captchaResponseParameterName) {
		this.captchaResponseParameterName = captchaResponseParameterName;
	}
}
