package fr.thetiptop.app.security.service;

import org.springframework.security.core.Authentication;

public interface AppJwtService {

    String createJwt(Authentication authentication);

    String getUid(String token);

    boolean validateToken(String token);
}

