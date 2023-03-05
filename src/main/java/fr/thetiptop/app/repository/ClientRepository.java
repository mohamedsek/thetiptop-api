package fr.thetiptop.app.repository;

import fr.thetiptop.app.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {
    ClientModel findByEmail(String email);

    @Query(value = "SELECT W.* FROM user W JOIN (SELECT U.id id FROM user U JOIN ticket T ON U.id = T.client_id WHERE T.is_participating=TRUE GROUP BY U.id LIMIT :start,1) WC ON WC.id=W.id", nativeQuery = true)
    ClientModel findRandomClientParticipating(@Param("start") long startPosition);

}
