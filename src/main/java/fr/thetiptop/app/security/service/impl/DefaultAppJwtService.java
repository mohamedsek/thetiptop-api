package fr.thetiptop.app.security.service.impl;

import fr.thetiptop.app.config.PropertiesConfig;
import fr.thetiptop.app.security.AppJwtService;
import fr.thetiptop.app.security.AppUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Service
public class DefaultAppJwtService implements AppJwtService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultAppJwtService.class);
    @Autowired
    private PropertiesConfig propertiesConfig;


    @Value("${spring.application.name:The-tip-top}")
    private String appName;

    @Override
    public String createJwt(AppUserDetails appUserDetails) {
        String token = Jwts.builder()
                .setIssuer(this.appName)
                .setSubject(appUserDetails.getUid())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(propertiesConfig.getJwt().getMinuteExpireTime(), ChronoUnit.MINUTES)))
                .signWith(
                        SignatureAlgorithm.HS256,
                        Base64.getDecoder().decode(propertiesConfig.getJwt().getSecret())
                )
                .compact();
        return token;
    }

    @Override
    public String getUid(final String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(propertiesConfig.getJwt().getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @Override
    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(propertiesConfig.getJwt().getSecret()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            LOG.error("Invalid token");
        } catch (SignatureException ex) {
            LOG.error("token Signature not valid");
        } catch (ExpiredJwtException ex) {
            LOG.error("token expired");
        } catch (UnsupportedJwtException ex) {
            LOG.error("Unsupported token");
        } catch (IllegalArgumentException ex) {
            LOG.error("Token claims empty");
        }
        return false;
    }

    public void setPropertiesConfig(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }
}
