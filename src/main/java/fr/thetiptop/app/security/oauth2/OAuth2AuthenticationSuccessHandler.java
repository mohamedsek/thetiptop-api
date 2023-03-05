package fr.thetiptop.app.security.oauth2;

import fr.thetiptop.app.config.PropertiesConfig;
import fr.thetiptop.app.constant.Constants;
import fr.thetiptop.app.security.service.AppJwtService;
import fr.thetiptop.app.util.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private AppJwtService appJwtService;
    @Autowired
    private PropertiesConfig propertiesConfig;

    @Value("${app.default.success.url}")
    private String defaultSuccessUrl;
    @Value("${app.default.error.url}")
    private String defaultErrorUrl;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUriOpt = CookieUtils.getCookie(request, Constants.Oauth2.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        if (redirectUriOpt.isPresent() && !isAuthorizedRedirectUri(redirectUriOpt.get())) {
            logger.error("Unauthorized redirect url found : " + redirectUriOpt.get());
            return defaultErrorUrl;
        }

        String targetUrl = redirectUriOpt.orElse(this.defaultSuccessUrl);
        String token = appJwtService.createJwt(authentication);
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }


    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        List<String> authorizedRedirectUris = propertiesConfig.getOauth2().getAuthorizedRedirectUris();
        return authorizedRedirectUris.stream().anyMatch(authorizedRedirectUri -> {
            URI authorizedURI = URI.create(authorizedRedirectUri);
            return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) && authorizedURI.getPort() == clientRedirectUri.getPort();
        });
    }
}
