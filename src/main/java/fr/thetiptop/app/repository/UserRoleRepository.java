package fr.thetiptop.app.repository;

import fr.thetiptop.app.models.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleModel, Long> {
    UserRoleModel findByName(String clientRole);
}
