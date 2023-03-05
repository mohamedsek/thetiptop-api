package fr.thetiptop.app.security.oauth2.user;


import fr.thetiptop.app.models.AuthenticationProvider;
import fr.thetiptop.app.security.oauth2.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthenticationProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Login with " + registrationId + " is not supported.");
        }
    }
}
