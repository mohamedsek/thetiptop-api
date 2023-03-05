package fr.thetiptop.app.controller;


import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.dto.auth.AuthResponseDto;
import fr.thetiptop.app.dto.auth.SignInRequestDto;
import fr.thetiptop.app.dto.auth.SignUpRequestDto;
import fr.thetiptop.app.facades.UserFacade;
import fr.thetiptop.app.security.service.AppJwtService;
import fr.thetiptop.app.security.service.AppUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication/Registration", description = "Authentication/Registration")
public class AuthenticationController {
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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = appJwtService.createJwt((AppUserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(AuthResponseDto.builder().accessToken(token).build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewClient(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {

        // TODO add signUpRequestDto validation
        if (userFacade.existsByEmail(signUpRequestDto.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        if (StringUtils.isBlank(signUpRequestDto.getEmail())
                || StringUtils.isBlank(signUpRequestDto.getPassword())
                || !StringUtils.equalsAnyIgnoreCase(signUpRequestDto.getPassword(), signUpRequestDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().build();
        }
        UserDto register = userFacade.registerClient(signUpRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo() {
        Optional<UserDto> currentUser = userFacade.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }
}

