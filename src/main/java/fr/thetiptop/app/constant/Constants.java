package fr.thetiptop.app.constant;

public class Constants {

    private Constants() {
    }

    public static class Roles {
        public static final String CUSTOMER = "CUSTOMER";
        public static final String CHECKOUT_MACHINE = "CHECKOUT_MACHINE";
        public static final String ADMIN = "ADMIN";
        public static final String ROLE_PREFIX = "ROLE_";

    }

    public static class Oauth2 {
        public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
        public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";

    }
}
