/* $Header: $
 * Created on 21.11.2005
 * Copyright (c) 2005 Tribeo 
 */
package cz.sweb.pichlik.springtutorial.captcha;

/**
 * JavaBean which represents comment.
 * 
 * @author 	 Roman Pichlík
 * @version  $Revision: 1.0 $ 
 * @since 	 1.0
 */
public class Comment {
	private String email;
	private String subject;
	private String body;
	
	/**
	 * @return Returns the body.
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body The body to set.
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}	
}
