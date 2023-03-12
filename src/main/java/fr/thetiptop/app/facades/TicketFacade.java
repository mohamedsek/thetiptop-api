package fr.thetiptop.app.facades;

import fr.thetiptop.app.dto.TicketGainDto;

import java.util.List;

public interface TicketFacade {

    List<TicketGainDto> getCurrentUserGains();
}
