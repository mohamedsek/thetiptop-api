package fr.thetiptop.app.models;

import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "Administrator")
@Data
@NoArgsConstructor
public class AdminModel extends UserModel {

    @Builder
    public AdminModel(Long id, String email, String uid, String firstName, String lastName, String password, UserRoleModel role, boolean enabled) {
        super(id, email, uid, firstName, lastName, password, role, enabled);
    }
}
