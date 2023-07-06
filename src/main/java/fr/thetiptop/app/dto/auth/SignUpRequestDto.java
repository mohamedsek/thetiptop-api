package fr.thetiptop.app.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    @Email(message = "L'adresse email est invalide.")
    @NotBlank(message = "L'adresse email est obligatoire.")
    private String email;
    @NotBlank(message = "Le prénom est obligatoire.")
    private String firstName;
    @NotBlank(message = "Le nom est obligatoire.")
    private String lastName;
    @NotBlank(message = "Le mot de passe est obligatoire.")
    private String password;
    @NotBlank(message = "La Confirmation du mot de passe est obligatoire.")
    private String confirmPassword;


}