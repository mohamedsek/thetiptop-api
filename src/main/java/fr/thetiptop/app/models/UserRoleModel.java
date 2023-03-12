package fr.thetiptop.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "UserRole")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<UserModel> usersModel;

}
