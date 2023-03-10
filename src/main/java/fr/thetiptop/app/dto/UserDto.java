package fr.thetiptop.app.dto;

import fr.thetiptop.app.models.UserRoleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String uid;
    private String firstName;
    private String lastName;
    private UserRoleModel role;
}
