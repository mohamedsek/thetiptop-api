package fr.thetiptop.app.models;

import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "CheckoutMachine")
@Data
@NoArgsConstructor
public class CheckoutMachineModel extends UserModel {
    @Builder
    public CheckoutMachineModel(Long id, String email, String uid, String firstName, String lastName, String password, UserRoleModel role, boolean enabled) {
        super(id, email, uid, firstName, lastName, password, role, enabled);
    }
}
