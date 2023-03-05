package fr.thetiptop.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
                .authorizeRequests()
                .requestMatchers("/error",
                        "/*/*.jpg",
                        "/*/*.png",
                        "/*/*.svg",
                        "/*/*.gif",
                        "/*/*.html",
                        "/*/*.css",
                        "/*/*.js",
                        "/doc/*").permitAll()
                .requestMatchers("/auth/*", "/auth2/*").permitAll()
                .anyRequest().authenticated();


        return httpSecurity.build();
    }
}
