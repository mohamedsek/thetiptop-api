package fr.thetiptop.app.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
@Getter
public class PropertiesConfig {
    private final Cors cors = new Cors();
    private final Jwt jwt = new Jwt();
    private final OAuth2 oauth2 = new OAuth2();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Cors {
        private String allowedOrigins;
        private int maxAge;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Jwt {
        private String secret;
        private int minuteExpireTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();
    }
}

