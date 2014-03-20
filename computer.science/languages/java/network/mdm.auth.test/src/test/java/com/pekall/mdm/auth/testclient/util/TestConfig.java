package com.pekall.mdm.auth.testclient.util;

public class TestConfig {

    public enum Profile{ TEST, DEVELOP, PRODUCTION };

    public static final Profile PROFILE = Profile.PRODUCTION;

    static {
        if (Profile.TEST == PROFILE) {

            NGNIX_ADDR = "http://localhost:8080";
            AUTH_SVR_ADDR = "http://localhost:8080";
            MEMCACHED_SERVERS = "localhost:11211";
            MDM_SVR_ADDR = "http://localhost:8082";

            USERNAME_VALID = "tom@pekall.com";
            PASSWORD_OLD = "tom";

        } else if (Profile.DEVELOP == PROFILE){

            NGNIX_ADDR = "http://localhost:8080";
            AUTH_SVR_ADDR = "http://localhost:8080";
            MEMCACHED_SERVERS = "localhost:11211";
            MDM_SVR_ADDR = "http://192.168.10.34:8080";

            USERNAME_VALID = "rui.jiang@pekall.com";

            // FIXME: use following command to reset password and get it from email
            // curl -d "username=jiangrui&email=rui.jiang@pekall.com" http://192.168.10.34:8080/mdm/v1/personal_admin/reset_password
            PASSWORD_OLD = "qFPWm5cT";
        } else if (Profile.PRODUCTION == PROFILE) {

            NGNIX_ADDR = "http://192.168.10.32:80";
            AUTH_SVR_ADDR = "http://192.168.10.33:8080";
            MEMCACHED_SERVERS = "192.168.10.34:11211 192.168.10.22:11211";
            MDM_SVR_ADDR = "http://192.168.10.32:80";

            USERNAME_VALID = "rui.jiang@pekall.com";

            // FIXME: use following command to reset password and get it from email
            // curl -d "username=jiangrui&email=rui.jiang@pekall.com" http://192.168.10.34:8080/mdm/v1/personal_admin/reset_password
            PASSWORD_OLD = "HqPiCv8j";
        }
    }

    public static String NGNIX_ADDR;
    public static String AUTH_SVR_ADDR;

    public static String AUTH_LOGIN_PATH = "/auth/v1/login";
    public static String AUTH_LOGOUT_PATH = "/auth/v1/logout";
    public static String AUTH_RENEW_USER_PATH = "/auth/v1/renew_user";
    public static String AUTH_USER_INFO_PATH = "/auth/v1/internal/get_user_info";

    public static String MDM_TEST_PATH =
            "/mdm/v1/personal_admin/account_and_password";

    public static String AUTH_LOGIN_URL = NGNIX_ADDR + AUTH_LOGIN_PATH;
    public static String AUTH_LOGOUT_URL = NGNIX_ADDR + AUTH_LOGOUT_PATH;

    public static String AUTH_RENEW_USER_URL = AUTH_SVR_ADDR + AUTH_RENEW_USER_PATH;
    public static String AUTH_GET_USER_INFO_URL = AUTH_SVR_ADDR + AUTH_USER_INFO_PATH;

    public static String PARAM_USERNAME = "email";
    public static String PARAM_PASSWORD = "password";
    public static String PARAM_OLD_PASSWORD = "oldPassword";
    public static String PARAM_NEW_PASSWORD = "newPassword";

    public static String USERNAME_VALID;   // valid username
    public static String PASSWORD_OLD;   // valid password
    public static String PASSWORD_NEW = "abcdefg";   // valid password

    public static String RENEW_USER_PARAM = "?email=rui.jiang@pekall.com";
    public static String RENEW_USER_PARAM_ERR = "?email=xxx";

    public static String HTTP_HDR_SET_COOKIE = "Set-Cookie";
    public static String HTTP_HDR_COOKIE = "Cookie";

    // mdm service information
    public static String MDM_SVR_ADDR;
    public static String MDM_PS_ADMIN_PATH = "/mdm/v1/personal_admin";

    public static String MDM_PS_ADMIN_CHANGE_PWS_URL =
            MDM_SVR_ADDR + MDM_PS_ADMIN_PATH + "/modify_password";
    public static String MDM_PS_ADMIN_RESET_PWS_URL =
            MDM_SVR_ADDR + MDM_PS_ADMIN_PATH + "/reset_password";

    // memcached servers information
    public static String MEMCACHED_SERVERS;
}
