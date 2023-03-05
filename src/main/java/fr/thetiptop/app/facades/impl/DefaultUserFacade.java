package fr.thetiptop.app.facades.impl;


import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.dto.auth.SignUpRequestDto;
import fr.thetiptop.app.facades.UserFacade;
import fr.thetiptop.app.mapper.UserMapper;
import fr.thetiptop.app.models.UserModel;
import fr.thetiptop.app.models.UserRoleModel;
import fr.thetiptop.app.repository.UserRepository;
import fr.thetiptop.app.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultUserFacade implements UserFacade {

    public static final String ROLE_USER = "ROLE_USER";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<UserModel> byEmailEntity = userRepository.findByEmail(email);
        if (byEmailEntity.isPresent()) {
            UserModel userModel = byEmailEntity.get();
            return Optional.ofNullable(UserMapper.INSTANCE.mapToDto(userModel));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findByUid(String uid) {
        Optional<UserModel> byUidOpt = userRepository.findByUid(uid);
        if (byUidOpt.isPresent()) {
            UserModel userModel = byUidOpt.get();
            return Optional.ofNullable(UserMapper.INSTANCE.mapToDto(userModel));
        }
        return Optional.empty();
    }

    @Override
    public UserDto register(SignUpRequestDto signUpRequestDto) {
        Optional<UserRoleModel> roleUser = userRoleRepository.findByName(ROLE_USER);
        UserModel userModel = UserModel.builder()
                .email(signUpRequestDto.getEmail())
                .firstName(signUpRequestDto.getFirstName())
                .lastName(signUpRequestDto.getLastName())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .uid(UUID.randomUUID().toString())
                .role(roleUser.get())
                .enabled(true)
                .build();
        UserModel save = userRepository.save(userModel);
        return UserMapper.INSTANCE.mapToDto(save);
    }
}
