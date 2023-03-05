package fr.thetiptop.app.service;

import fr.thetiptop.app.models.TicketModel;

import java.util.Optional;

public interface TicketService {


    TicketModel createTicket();

    Optional<TicketModel> findByCode(String ticketCode);

    TicketModel save(TicketModel ticketModel);
}
