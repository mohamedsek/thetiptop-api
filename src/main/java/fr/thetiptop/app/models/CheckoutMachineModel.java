package fr.thetiptop.app.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "CheckoutMachine")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutMachineModel extends UserModel {
}
