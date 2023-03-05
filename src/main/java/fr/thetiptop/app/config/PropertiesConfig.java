package fr.thetiptop.app.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Data
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

