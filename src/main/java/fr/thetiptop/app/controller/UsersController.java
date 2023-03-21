package fr.thetiptop.app.controller;


import fr.thetiptop.app.dto.ResponseDto;
import fr.thetiptop.app.dto.ResponseStatuses;
import fr.thetiptop.app.dto.auth.SignUpResponseDto;
import fr.thetiptop.app.dto.auth.UpdateUserPasswordDto;
import fr.thetiptop.app.dto.auth.UpdateUserProfileDto;
import fr.thetiptop.app.facades.UserFacade;
import fr.thetiptop.app.security.service.AppJwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users Controller", description = "Users Controller")
public class UsersController {
    private static final String CONFIRM_PASSWORD_MESSAGE_ERROR = "La confirmation du mot de passe ne correspond pas.";
    private static final String OLD_PASSWORD_INCORRECT_MESSAGE_ERROR = "L'ancien mot de passe est incorrect.";
    private static final String EMAIL_ALREADY_EXISTS_ERROR = "Cette adresse e-mail est déjà utilisée";
    private AuthenticationManager authenticationManager;
    private AppJwtService appJwtService;
    private UserFacade userFacade;
    private PasswordEncoder passwordEncoder;

    public UsersController(AuthenticationManager authenticationManager, AppJwtService appJwtService, UserFacade userFacade, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.appJwtService = appJwtService;
        this.userFacade = userFacade;
        this.passwordEncoder = passwordEncoder;
    }


    @DeleteMapping("/{uid}")
    public ResponseEntity<?> deleteUser(@PathVariable("uid") String uid) {
        if (userFacade.deleteByUid(uid)) {
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.badRequest().body("Invalid user UID");
    }

    @PutMapping("/profile/{uid}")
    public ResponseEntity<?> updateUser(@PathVariable("uid") String uid, @Valid @RequestBody UpdateUserProfileDto updateUserProfileDto, BindingResult bindingResult) {
        if (userFacade.updateUser(uid, updateUserProfileDto)) {
            return ResponseEntity.ok(ResponseDto.builder().message("User updated successfully").status(ResponseStatuses.Success.toString()).build());
        }
        return ResponseEntity.badRequest().body(ResponseDto.builder().message("Invalid user UID").status(ResponseStatuses.Failure.toString()).build());
    }

    @PutMapping("/password/{uid}")
    public ResponseEntity<?> changePassword(@PathVariable("uid") String uid, @Valid @RequestBody UpdateUserPasswordDto updateUserPasswordDto, BindingResult bindingResult) {
        if (!StringUtils.equals(updateUserPasswordDto.getPassword(), updateUserPasswordDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "confirmPasswordError", CONFIRM_PASSWORD_MESSAGE_ERROR);
        }
        if (!userFacade.isOldPasswordValid(uid, updateUserPasswordDto.getOldPassword())) {
            bindingResult.rejectValue("oldPassword", "oldPasswordError", OLD_PASSWORD_INCORRECT_MESSAGE_ERROR);
        }
        if (bindingResult.hasErrors()) {
            SignUpResponseDto failure = SignUpResponseDto.builder().status("Failure").errors(bindingResult.getAllErrors()).build();
            return ResponseEntity.badRequest().body(failure);
        }
        if (userFacade.updatePassword(uid, updateUserPasswordDto.getPassword())) {
            return ResponseEntity.ok(ResponseDto.builder().message("Password updated successfully").status(ResponseStatuses.Success.toString()).build());
        }
        return ResponseEntity.badRequest().body(ResponseDto.builder().message("Invalid user UID").status(ResponseStatuses.Failure.toString()).build());
    }
}

