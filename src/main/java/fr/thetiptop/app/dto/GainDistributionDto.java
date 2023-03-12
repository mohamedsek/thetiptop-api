package fr.thetiptop.app.dto;

public interface GainDistributionDto {

    Long getId();

    String getTitle();

    Integer getTargetDistribution();
    Integer getNumberOfParticipatingByGain();
    Integer getTotalParticipating();
    double getCurrentDistribution();

}
