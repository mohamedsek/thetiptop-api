package fr.thetiptop.app.controller;

import fr.thetiptop.app.dto.TicketDto;
import fr.thetiptop.app.mapper.TicketMapper;
import fr.thetiptop.app.models.TicketModel;
import fr.thetiptop.app.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketDto> createTicket() {
        final TicketModel ticketModel = ticketService.createTicket();
        return new ResponseEntity<TicketDto>(TicketMapper.INSTANCE.ticketToTicketDto(ticketModel), HttpStatus.CREATED);
    }

}
