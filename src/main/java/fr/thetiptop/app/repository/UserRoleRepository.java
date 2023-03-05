package fr.thetiptop.app.repository;

import fr.thetiptop.app.models.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoleModel, Long> {
    Optional<UserRoleModel> findByName(String clientRole);
}
