package fr.thetiptop.app.security.service;


import fr.thetiptop.app.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String uid;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

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

    public UserDetails create(UserModel user) {
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
            return List.of(new SimpleGrantedAuthority(user.getRole().getName()));
        }
        return Collections.emptyList();
    }
}
