package fr.thetiptop.app.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PropertiesConfig.class)
public class GlobalConfig {
    // add here global beans
}
