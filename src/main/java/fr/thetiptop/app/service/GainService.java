package fr.thetiptop.app.service;

import fr.thetiptop.app.dto.GainDistributionDto;
import fr.thetiptop.app.dto.GainDto;
import fr.thetiptop.app.models.ClientModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GainService {
    GainDto generateGain(String ticketCode);

    ClientModel selectJackpotWinner();

    List<GainDistributionDto> findCurrentDistributionPercentage();
}
