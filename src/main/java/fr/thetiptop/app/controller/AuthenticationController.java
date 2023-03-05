package fr.thetiptop.app.controller;


import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.dto.auth.AuthResponseDto;
import fr.thetiptop.app.dto.auth.SignInRequestDto;
import fr.thetiptop.app.dto.auth.SignUpRequestDto;
import fr.thetiptop.app.facades.UserFacade;
import fr.thetiptop.app.security.AppJwtService;
import fr.thetiptop.app.security.AppUserDetails;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
//@Tag(name = "Authentication/Registration", description = "Authentication/Registration")
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
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {

        // TODO add signUpRequestDto validation
        if (userFacade.existsByEmail(signUpRequestDto.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        if (StringUtils.isBlank(signUpRequestDto.getEmail())
                || StringUtils.isBlank(signUpRequestDto.getPassword())
                || !StringUtils.equalsAnyIgnoreCase(signUpRequestDto.getPassword(), signUpRequestDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().build();
        }
        UserDto register = userFacade.register(signUpRequestDto);
        return ResponseEntity.ok().build();
    }
}

