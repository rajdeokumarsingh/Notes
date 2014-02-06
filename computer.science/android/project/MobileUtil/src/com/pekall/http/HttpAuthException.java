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

public class HttpAuthException extends HttpRefusedException {

	@SuppressWarnings("UnusedDeclaration")
    private static final long serialVersionUID = 4206324519931063593L;

	@SuppressWarnings("UnusedDeclaration")
    public HttpAuthException(Exception cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("UnusedDeclaration")
    public HttpAuthException(String msg, Exception cause, int statusCode) {
		super(msg, cause, statusCode);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("UnusedDeclaration")
    public HttpAuthException(String msg, Exception cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("UnusedDeclaration")
    public HttpAuthException(String msg, int statusCode) {
		super(msg, statusCode);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("UnusedDeclaration")
    public HttpAuthException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
}
