package fr.thetiptop.app.models;


import jakarta.persistence.*;

@Entity(name = "User")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(nullable = true)
    protected String password;

    @ManyToOne(optional = false)
    protected UserRoleModel role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleModel getRole() {
        return role;
    }

    public void setRole(UserRoleModel role) {
        this.role = role;
    }
}
