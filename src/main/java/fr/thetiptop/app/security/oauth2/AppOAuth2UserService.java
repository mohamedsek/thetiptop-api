package fr.thetiptop.app.security.oauth2;

import fr.thetiptop.app.constant.Constants;
import fr.thetiptop.app.models.AuthenticationProvider;
import fr.thetiptop.app.models.ClientModel;
import fr.thetiptop.app.models.UserModel;
import fr.thetiptop.app.models.UserRoleModel;
import fr.thetiptop.app.repository.UserRepository;
import fr.thetiptop.app.repository.UserRoleRepository;
import fr.thetiptop.app.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import fr.thetiptop.app.security.oauth2.user.OAuth2UserInfo;
import fr.thetiptop.app.security.oauth2.user.OAuth2UserInfoFactory;
import fr.thetiptop.app.security.service.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Optional<UserModel> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        ClientModel user;
        if (userOptional.isPresent() && Objects.nonNull(userOptional.get().getRole()) && !Constants.Roles.CUSTOMER.equals(userOptional.get().getRole().getName())) {
            throw new OAuth2AuthenticationProcessingException("User Already exists with different Role (admin or checkoutMachine)");
        } else if (userOptional.isPresent() && Objects.nonNull(userOptional.get().getRole()) && Constants.Roles.CUSTOMER.equals(userOptional.get().getRole().getName())) {
            user = updateExistingUser((ClientModel) userOptional.get(), oAuth2UserInfo, oAuth2UserRequest);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return AppUserDetails.create(user, oAuth2User.getAttributes());
    }

    private ClientModel registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Optional<UserRoleModel> roleUser = userRoleRepository.findByName(Constants.Roles.CUSTOMER);
        ClientModel userEntity = ClientModel.builder()
                .email(oAuth2UserInfo.getEmail())
                .firstName(oAuth2UserInfo.getFirstName())
                .lastName(oAuth2UserInfo.getLastName())
                .uid(UUID.randomUUID().toString())
                .role(roleUser.get())
                .authenticationProvider(AuthenticationProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .enabled(true)
                .build();
        return userRepository.save(userEntity);
    }

    private ClientModel updateExistingUser(ClientModel existingUser, OAuth2UserInfo oAuth2UserInfo, OAuth2UserRequest oAuth2UserRequest) {
        AuthenticationProvider provider = AuthenticationProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        if (!existingUser.getAuthenticationProvider().equals(provider)) {
            existingUser.setAuthenticationProvider(provider);
        }
        existingUser.setFirstName(oAuth2UserInfo.getFirstName());
        existingUser.setLastName(oAuth2UserInfo.getLastName());
        return userRepository.save(existingUser);
    }

}
