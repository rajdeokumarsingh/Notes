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

@SuppressWarnings({"serial", "WeakerAccess"})
public class HttpRefusedException extends HttpException {
	// private RefuseError mError;

	@SuppressWarnings("WeakerAccess")
    public HttpRefusedException(Exception cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("WeakerAccess")
    public HttpRefusedException(String msg, Exception cause, int statusCode) {
		super(msg, cause, statusCode);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("WeakerAccess")
    public HttpRefusedException(String msg, Exception cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

	public HttpRefusedException(String msg, int statusCode) {
		super(msg, statusCode);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("WeakerAccess")
    public HttpRefusedException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	//
	// public RefuseError getError() {
	// return mError;
	// }
	//
	// public HttpRefusedException setError(RefuseError error) {
	// mError = error;
	// return this;
	// }

}
