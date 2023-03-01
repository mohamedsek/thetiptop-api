package fr.thetiptop.app.models;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "UserRole")
public class UserRoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<UserModel> usersModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserModel> getUsersModel() {
        return usersModel;
    }

    public void setUsersModel(List<UserModel> usersModel) {
        this.usersModel = usersModel;
    }
}
