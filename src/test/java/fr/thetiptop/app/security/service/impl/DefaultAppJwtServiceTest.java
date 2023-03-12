package fr.thetiptop.app.security.service.impl;

import fr.thetiptop.app.config.PropertiesConfig;
import fr.thetiptop.app.security.service.AppUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultAppJwtServiceTest {

    public static final String UID = "126654";
    public static final String JWT_SECRET = "amtxc2ZoO2prZXpoZmpremRoZmdiaGR6Z2Z6amhldmZsaXloZXpoa3Zma3V6ZWc0ZmtqNmc0Nmh2amg1NA==";
    @Mock
    private PropertiesConfig propertiesConfig;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private DefaultAppJwtService defaultAppJwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        PropertiesConfig.Jwt jwtConf = Mockito.mock(PropertiesConfig.Jwt.class);
        when(propertiesConfig.getJwt()).thenReturn(jwtConf);
        when(jwtConf.getMinuteExpireTime()).thenReturn(30);
        when(jwtConf.getSecret()).thenReturn(JWT_SECRET);
        AppUserDetails userDetails = new AppUserDetails();
        userDetails.setUid(UID);
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    void createJwtTest() {
        String jwt = defaultAppJwtService.createJwt(authentication);
        assertNotNull(jwt);
    }

    @Test
    void getUidTest() {
        String token = defaultAppJwtService.createJwt(authentication);
        String uid = defaultAppJwtService.getUid(token);
        assertEquals(UID, uid);
    }

    @Test
    void validateTokenTest() {
        String token = defaultAppJwtService.createJwt(authentication);
        assertTrue(defaultAppJwtService.validateToken(token));
    }

}