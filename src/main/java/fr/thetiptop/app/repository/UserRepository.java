package fr.thetiptop.app.repository;

import fr.thetiptop.app.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByUid(String uid);

    Boolean existsByEmail(String email);
}
