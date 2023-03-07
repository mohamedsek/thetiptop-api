package fr.thetiptop.app.constant;

public class Constants {

    private Constants() {
    }

    public static class Roles {
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String CHECKOUT_MACHINE = "ROLE_CHECKOUT_MACHINE";
        public static final String ADMIN = "ROLE_ADMIN";

    }

    public static class Oauth2 {
        public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
        public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";

    }
}
