package fr.thetiptop.app.repository;

import fr.thetiptop.app.models.GainModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GainRepository extends JpaRepository<GainModel, Long> {

}
