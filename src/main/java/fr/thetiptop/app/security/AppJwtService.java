package fr.thetiptop.app.security;

public interface AppJwtService {
    String createJwt(AppUserDetails appUserDetails);

    String getUid(String token);

    boolean validateToken(String token);
}

