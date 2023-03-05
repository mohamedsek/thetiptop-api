package fr.thetiptop.app.facades.impl;


import fr.thetiptop.app.constant.Constants;
import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.dto.auth.SignUpRequestDto;
import fr.thetiptop.app.facades.UserFacade;
import fr.thetiptop.app.mapper.UserMapper;
import fr.thetiptop.app.models.ClientModel;
import fr.thetiptop.app.models.UserModel;
import fr.thetiptop.app.models.UserRoleModel;
import fr.thetiptop.app.repository.UserRepository;
import fr.thetiptop.app.repository.UserRoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultUserFacade implements UserFacade {
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
    public UserDto registerClient(SignUpRequestDto signUpRequestDto) {
        Optional<UserRoleModel> roleUser = userRoleRepository.findByName(Constants.Roles.CUSTOMER);
        ClientModel userModel = ClientModel.builder()
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
    @Override
    public Optional<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication) && StringUtils.isNotBlank(authentication.getName())) {
            return this.findByEmail(authentication.getName());
        }
        return Optional.empty();
    }
}
