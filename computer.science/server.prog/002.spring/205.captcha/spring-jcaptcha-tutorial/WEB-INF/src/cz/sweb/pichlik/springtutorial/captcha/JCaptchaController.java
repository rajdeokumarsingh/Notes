/* $Header: $
 * Created on 21.11.2005
 * Copyright (c) 2005 Tribeo 
 */
package cz.sweb.pichlik.springtutorial.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
* JCaptcha controller which is responsible for generation and rendering of 
* captcha images. It is possible to configure controller with any type of
* <code>{@link com.octo.captcha.service.image.ImageCaptchaService}</code> 
* see <code>{@link #setCaptchaService(ImageCaptchaService)}</code>.
* <p>
* Based on article <a href="http://luminal.gotdns.com/confluence/display/general/5+minutes+application+integration+tutorial">5 minutes application integration tutorial</a> by <cite>Marc Antoine Garrigue</cite>
* 
* @author 	 Roman Pichlík
* @version  $Revision: 1.0 $ 
* @since 	 1.0
*/
public class JCaptchaController implements Controller, InitializingBean{
	private ImageCaptchaService captchaService;

	/**
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		// the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        
    	// get the session id that will identify the generated captcha. 
    	//the same id must be used to validate the response, the session id is a good candidate!
    	String captchaId = request.getSession().getId();
    	
    	// call the ImageCaptchaService getChallenge method
       	BufferedImage challenge =
                captchaService.getImageChallengeForID(captchaId,request.getLocale());
        
        // a jpeg encoder
        JPEGImageEncoder jpegEncoder =
                JPEGCodec.createJPEGEncoder(jpegOutputStream);
        jpegEncoder.encode(challenge);
     

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // flush it in the response
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
        	response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
        return null;
	}

	/**
	 * Set captcha service
	 * @param captchaService The captchaService to set.
	 */
	public void setCaptchaService(ImageCaptchaService captchaService) {
		this.captchaService = captchaService;		
	}	
	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if(captchaService == null){
			throw new RuntimeException("Image captcha service wasn`t set!");
		}
	}
}
