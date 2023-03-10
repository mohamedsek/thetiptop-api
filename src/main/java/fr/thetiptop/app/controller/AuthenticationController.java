package fr.thetiptop.app.controller;


import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.dto.auth.AuthResponseDto;
import fr.thetiptop.app.dto.auth.SignInRequestDto;
import fr.thetiptop.app.dto.auth.SignUpRequestDto;
import fr.thetiptop.app.dto.auth.SignUpResponseDto;
import fr.thetiptop.app.facades.UserFacade;
import fr.thetiptop.app.security.service.AppJwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication/Registration", description = "Authentication/Registration")
public class AuthenticationController {
    private static final String CONFIRM_PASSWORD_MESSAGE_ERROR = "La confirmation du mot de passe ne correspond pas.";
    private static final String EMAIL_ALREADY_EXISTS_ERROR = "Cette adresse e-mail est déjà utilisée";
    private AuthenticationManager authenticationManager;

    private AppJwtService appJwtService;

    private UserFacade userFacade;

    public AuthenticationController(AuthenticationManager authenticationManager, AppJwtService appJwtService, UserFacade userFacade) {
        this.authenticationManager = authenticationManager;
        this.appJwtService = appJwtService;
        this.userFacade = userFacade;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInRequestDto signInRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = appJwtService.createJwt(authentication);
            return ResponseEntity.ok(AuthResponseDto.builder().accessToken(token).build());
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(AuthResponseDto.builder().errorMessage("Bad credentials").build());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewClient(@Valid @RequestBody SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {

        if (userFacade.existsByEmail(signUpRequestDto.getEmail())) {
            bindingResult.rejectValue("email", "emailExists", EMAIL_ALREADY_EXISTS_ERROR);
        }
        if (StringUtils.isBlank(signUpRequestDto.getEmail())
                || StringUtils.isBlank(signUpRequestDto.getPassword())
                || !StringUtils.equalsAnyIgnoreCase(signUpRequestDto.getPassword(), signUpRequestDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "confirmPasswordError", CONFIRM_PASSWORD_MESSAGE_ERROR);
        }
        if (bindingResult.hasErrors()) {
            SignUpResponseDto failure = SignUpResponseDto.builder().status("Failure").errors(bindingResult.getAllErrors()).build();
            return ResponseEntity.badRequest().body(failure);
        }
        UserDto register = userFacade.registerClient(signUpRequestDto);
        return ResponseEntity.ok(SignUpResponseDto.builder().status("Success").build());
    }

    @PostMapping("/registermachine")
    public ResponseEntity<?> registerNewMachine(@Valid @RequestBody SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {

        if (userFacade.existsByEmail(signUpRequestDto.getEmail())) {
            bindingResult.rejectValue("email", "emailExists", EMAIL_ALREADY_EXISTS_ERROR);
        }
        if (StringUtils.isBlank(signUpRequestDto.getEmail())
                || StringUtils.isBlank(signUpRequestDto.getPassword())
                || !StringUtils.equalsAnyIgnoreCase(signUpRequestDto.getPassword(), signUpRequestDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "confirmPasswordError", CONFIRM_PASSWORD_MESSAGE_ERROR);
        }
        if (bindingResult.hasErrors()) {
            SignUpResponseDto failure = SignUpResponseDto.builder().status("Failure").errors(bindingResult.getAllErrors()).build();
            return ResponseEntity.badRequest().body(failure);
        }
        UserDto register = userFacade.registerMachine(signUpRequestDto);
        return ResponseEntity.ok(SignUpResponseDto.builder().status("Success").build());
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo() {
        Optional<UserDto> currentUser = userFacade.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }
}

