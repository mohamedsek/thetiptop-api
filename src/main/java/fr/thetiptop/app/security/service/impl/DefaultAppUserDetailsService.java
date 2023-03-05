package fr.thetiptop.app.security.service.impl;


import fr.thetiptop.app.models.UserModel;
import fr.thetiptop.app.repository.UserRepository;
import fr.thetiptop.app.security.AppUserDetails;
import fr.thetiptop.app.security.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultAppUserDetailsService implements AppUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new AppUserDetails().create(user);
    }

    @Override
    public UserDetails loadUserByUid(String uid) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUid(uid).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new AppUserDetails().create(user);
    }

}


