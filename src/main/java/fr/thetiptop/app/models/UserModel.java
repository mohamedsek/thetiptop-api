package fr.thetiptop.app.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity(name = "User")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Column(nullable = false, unique = true)
    protected String email;
    @Column(nullable = false, unique = true)
    protected String uid;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false, unique = true)
    private String lastName;

    @Column(nullable = true)
    protected String password;
    @ManyToOne(optional = false)
    protected UserRoleModel role;
    private boolean enabled;
}
