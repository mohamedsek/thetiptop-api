package fr.thetiptop.app.facades.impl;

import fr.thetiptop.app.dto.TicketGainDto;
import fr.thetiptop.app.facades.TicketFacade;
import fr.thetiptop.app.models.TicketModel;
import fr.thetiptop.app.service.TicketService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultTicketFacade implements TicketFacade {

    private TicketService ticketService;

    public DefaultTicketFacade(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @Override
    public List<TicketGainDto> getCurrentUserGains() {
        List<TicketModel> userTickets = ticketService.getCurrentUserGains();

        return userTickets.stream().map(ticket -> new TicketGainDto(ticket.getId(), ticket.getCode(), ticket.getGain().getTitle(), ticket.getIsUsed())).collect(Collectors.toList());
    }

    public List<TicketGainDto> getUserGains(String email) {

        List<TicketModel> userTickets = ticketService.getUserGains(email);

        return userTickets.stream().map(ticket -> new TicketGainDto(ticket.getId(), ticket.getCode(), ticket.getGain().getTitle(), ticket.getIsUsed())).collect(Collectors.toList());
    }

}
