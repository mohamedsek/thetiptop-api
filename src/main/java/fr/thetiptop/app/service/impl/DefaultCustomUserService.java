package fr.thetiptop.app.service.impl;

import fr.thetiptop.app.models.UserModel;
import fr.thetiptop.app.repository.UserRepository;
import fr.thetiptop.app.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultCustomUserService implements CustomUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserModel> getCurrentUser() {
        String username = getCurrentUsername();
        Optional<UserModel> userModel = Optional.empty();
        if (Objects.nonNull(username)) {
            userModel = userRepository.findByEmail(username);
        }
        return userModel;
    }

    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return authentication.getName();
    }

}
