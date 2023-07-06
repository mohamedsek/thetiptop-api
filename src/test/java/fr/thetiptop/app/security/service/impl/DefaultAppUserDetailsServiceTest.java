package fr.thetiptop.app.security.service.impl;

import fr.thetiptop.app.models.UserModel;
import fr.thetiptop.app.models.UserRoleModel;
import fr.thetiptop.app.repository.UserRepository;
import fr.thetiptop.app.security.service.AppUserDetails;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DefaultAppUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultAppUserDetailsService userDetailsService;

    private UserModel user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new UserModel();
        user.setEmail("test@test.com");
        user.setUid("123456");
        UserRoleModel role = new UserRoleModel();
        role.setName("un_role");
        user.setRole(role);
    }


    @Test
    void loadUserByUsername_userExists_returnsUserDetails() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        AppUserDetails appUserDetails = (AppUserDetails) userDetailsService.loadUserByUsername("test@test.com");
        assertEquals(user.getEmail(), appUserDetails.getUsername());
    }

    @Test
    void loadUserByUsername_userDoesNotExist_throwsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("test@test.com"));
    }

    @Test
    void loadUserByUid_userExists_returnsUserDetails() {
        when(userRepository.findByUid(anyString())).thenReturn(Optional.of(user));
        AppUserDetails appUserDetails = (AppUserDetails) userDetailsService.loadUserByUid("123456");
        assertEquals(user.getUid(), appUserDetails.getUid());
        assertEquals(user.getEmail(), appUserDetails.getEmail());
    }

    @Test
    void loadUserByUid_userDoesNotExist_throwsException() {
        when(userRepository.findByUid(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUid("123456"));
    }
}
