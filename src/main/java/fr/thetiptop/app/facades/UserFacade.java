package fr.thetiptop.app.facades;



import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.dto.auth.SignUpRequestDto;

import java.util.Optional;

public interface UserFacade {

    Boolean existsByEmail(String email);

    Optional<UserDto> findByEmail(String email);

    Optional<UserDto> findByUid(String uid);

    UserDto registerClient(SignUpRequestDto signUpRequestDto);

    Optional<UserDto> getCurrentUser();
}

