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
public class UpdateUserProfileDto {
    @NotBlank(message = "Le prénom est obligatoire.")
    private String firstName;
    @NotBlank(message = "Le nom est obligatoire.")
    private String lastName;
}