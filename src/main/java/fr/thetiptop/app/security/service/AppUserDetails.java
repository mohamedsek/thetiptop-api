package fr.thetiptop.app.security.service;


import fr.thetiptop.app.constant.Constants;
import fr.thetiptop.app.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppUserDetails implements UserDetails, OAuth2User {

    private Long id;
    private String email;
    private String uid;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private Map<String, Object> attributes;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public String getName() {
        return getUid();
    }


    public static UserDetails create(UserModel user) {
        List<SimpleGrantedAuthority> authorities = getAuthorities(user);
        return AppUserDetails.builder()
                .authorities(authorities)
                .id(user.getId())
                .email(user.getEmail())
                .uid(user.getUid())
                .password(user.getPassword())
                .build();
    }

    private static List<SimpleGrantedAuthority> getAuthorities(UserModel user) {
        if (Objects.nonNull(user)) {
            return List.of(new SimpleGrantedAuthority(Constants.Roles.ROLE_PREFIX + user.getRole().getName()));
        }
        return Collections.emptyList();
    }

    public static OAuth2User create(UserModel user, Map<String, Object> attributes) {
        AppUserDetails userDetails = (AppUserDetails) AppUserDetails.create(user);
        userDetails.setAttributes(attributes);
        return userDetails;
    }
}
