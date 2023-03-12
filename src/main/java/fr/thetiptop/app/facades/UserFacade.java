package fr.thetiptop.app.facades;



import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.dto.auth.SignUpRequestDto;
import fr.thetiptop.app.models.ClientModel;

import java.util.Optional;

public interface UserFacade {

    ClientModel getRandomClient();

    Boolean existsByEmail(String email);

    Optional<UserDto> findByEmail(String email);

    Optional<UserDto> findByUid(String uid);

    UserDto registerClient(SignUpRequestDto signUpRequestDto);

    UserDto registerMachine(SignUpRequestDto signUpRequestDto);

    UserDto registerAdmin(SignUpRequestDto signUpRequestDto);

    Optional<UserDto> getCurrentUser();
}

