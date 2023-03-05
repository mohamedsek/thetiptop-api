package fr.thetiptop.app.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Getter
public class PropertiesConfig {
    private final Cors cors = new Cors();
    private final Jwt jwt = new Jwt();

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
}

