package fr.thetiptop.app.security.service;

public interface AppJwtService {
    String createJwt(AppUserDetails appUserDetails);

    String getUid(String token);

    boolean validateToken(String token);
}

