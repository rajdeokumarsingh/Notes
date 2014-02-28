package com.pekall.mdm.util;

public class Const {

    public static final String AUTH_SVR_ADDR = "http://192.168.8.150:8080";
    public static final String AUTH_LOGIN_PATH = "/rest/auth/login";
    public static final String AUTH_LOGOUT_PATH = "/rest/auth/logout";

    public static final String AUTH_LOGIN_URL = AUTH_SVR_ADDR + AUTH_LOGIN_PATH;
    public static final String AUTH_LOGOUT_URL = AUTH_SVR_ADDR + AUTH_LOGOUT_PATH;

    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";

    public static final String USERNAME_OK = "jack";   // valid username
    public static final String PASSWORD_OK = "jack";   // valid password

    public static final String HTTP_HDR_SET_COOKIE = "Set-Cookie";
    public static final String HTTP_HDR_COOKIE = "Cookie";
    public static final String HTTP_HDR_CONTENT_LENGTH = "Content-Length";

}
