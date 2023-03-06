package fr.thetiptop.app.service;

import fr.thetiptop.app.models.TicketModel;

import java.util.Optional;

public interface TicketService {

    String JACKPOT_TICKET_CODE = "0000-0000-0000-0000";

    TicketModel createTicket();

    Optional<TicketModel> findByCode(String ticketCode);

    TicketModel save(TicketModel ticketModel);

    void removeJackpotTicket();
}
