package fr.thetiptop.app.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPasswordDto {
    @NotBlank(message = "L'ancien mot de passe est obligatoire.")
    private String oldPassword;
    @NotBlank(message = "Le mot de passe est obligatoire.")
    private String password;
    @NotBlank(message = "La Confirmation du mot de passe est obligatoire.")
    private String confirmPassword;

}