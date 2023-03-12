package fr.thetiptop.app.repository;

import fr.thetiptop.app.dto.GainDistributionDto;
import fr.thetiptop.app.models.GainModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GainRepository extends JpaRepository<GainModel, Long> {

    @Query(value = " SELECT g.id, g.title, g.chance AS targetDistribution, COUNT(g.id) as numberOfParticipatingByGain, STP.totalParticipating, (COUNT(g.id) / STP.totalParticipating) * 100 AS currentDistribution\n" +
            "FROM gain AS g JOIN ticket t\n" +
            "\tON t.gain_id = g.id\n" +
            "\tJOIN  (SELECT COUNT(*) AS totalParticipating FROM ticket AS TP WHERE TP.is_participating = true ) AS STP\n" +
            "WHERE t.is_participating = true AND t.code != '0000-0000-0000-0000'\n" +
            "GROUP BY g.id, g.title, g.chance, STP.totalParticipating", nativeQuery = true)
    List<GainDistributionDto> findCurrentDistributionPercentage();

    GainModel findOneByChance(double chanceValue);

}
