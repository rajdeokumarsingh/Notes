/* ---------------------------------------------------------------------------------------------
 *                       Copyright (c) 2013 Capital Alliance Software(Pekall) 
 *                                    All Rights Reserved
 *    NOTICE: All information contained herein is, and remains the property of Pekall and
 *      its suppliers,if any. The intellectual and technical concepts contained herein are
 *      proprietary to Pekall and its suppliers and may be covered by P.R.C, U.S. and Foreign
 *      Patents, patents in process, and are protected by trade secret or copyright law.
 *      Dissemination of this information or reproduction of this material is strictly 
 *      forbidden unless prior written permission is obtained from Pekall.
 *                                     www.pekall.com
 *--------------------------------------------------------------------------------------------- 
*/


package com.pekall.http;

/**
 * HTTP StatusCode is not 200
 */
@SuppressWarnings({"serial", "WeakerAccess"})
public class HttpException extends Exception {

	private int statusCode = -1;

	public HttpException(String msg) {
		super(msg);
	}

	public HttpException(Exception cause) {
		super(cause);
	}

	public HttpException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}

	public HttpException(String msg, Exception cause) {
		super(msg, cause);
	}

	public HttpException(String msg, Exception cause, int statusCode) {
		super(msg, cause);
		this.statusCode = statusCode;
	}

	@SuppressWarnings("UnusedDeclaration")
    public int getStatusCode() {
		return this.statusCode;
	}

}
