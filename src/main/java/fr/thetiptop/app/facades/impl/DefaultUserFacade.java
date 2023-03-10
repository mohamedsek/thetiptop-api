package fr.thetiptop.app.facades.impl;


import fr.thetiptop.app.constant.Constants;
import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.dto.auth.SignUpRequestDto;
import fr.thetiptop.app.facades.UserFacade;
import fr.thetiptop.app.mapper.UserMapper;
import fr.thetiptop.app.models.*;
import fr.thetiptop.app.repository.ClientRepository;
import fr.thetiptop.app.repository.UserRepository;
import fr.thetiptop.app.repository.UserRoleRepository;
import fr.thetiptop.app.util.GameUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ClientModel getRandomClient() {
        // TODO : Fix count participating clients only
        long clientsCount = clientRepository.count();
        int selectRowIndex = GameUtil.randomValue(0, Long.valueOf(clientsCount).intValue());
        logger.debug("selected random client index: " + selectRowIndex);
        return clientRepository.findRandomClientParticipating(selectRowIndex);
    }
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

    public UserDto registerMachine(SignUpRequestDto signUpRequestDto) {
        Optional<UserRoleModel> roleUser = userRoleRepository.findByName(Constants.Roles.CHECKOUT_MACHINE);
        UserModel checkoutMachineModel = CheckoutMachineModel.builder()
                .email(signUpRequestDto.getEmail())
                .firstName(signUpRequestDto.getFirstName())
                .lastName(signUpRequestDto.getLastName())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .uid(UUID.randomUUID().toString())
                .role(roleUser.get())
                .enabled(true)
                .build();
        UserModel save = userRepository.save(checkoutMachineModel);
        return UserMapper.INSTANCE.mapToDto(save);
    }

    public UserDto registerAdmin(SignUpRequestDto signUpRequestDto) {
        Optional<UserRoleModel> roleUser = userRoleRepository.findByName(Constants.Roles.ADMIN);
        UserModel adminModel = AdminModel.builder()
                .email(signUpRequestDto.getEmail())
                .firstName(signUpRequestDto.getFirstName())
                .lastName(signUpRequestDto.getLastName())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .uid(UUID.randomUUID().toString())
                .role(roleUser.get())
                .enabled(true)
                .build();
        UserModel save = userRepository.save(adminModel);
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
