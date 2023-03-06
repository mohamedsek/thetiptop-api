package fr.thetiptop.app.controller;

import fr.thetiptop.app.dto.GainDto;
import fr.thetiptop.app.dto.TicketDto;
import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.mapper.TicketMapper;
import fr.thetiptop.app.mapper.UserMapper;
import fr.thetiptop.app.models.TicketModel;
import fr.thetiptop.app.service.GainService;
import fr.thetiptop.app.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private GainService gainService;

    private TicketService ticketService;

    public TicketController(GainService gainService, TicketService ticketService) {
        this.gainService = gainService;
        this.ticketService = ticketService;
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketDto> createTicket() {
        final TicketModel ticketModel = ticketService.createTicket();
        return new ResponseEntity<TicketDto>(TicketMapper.INSTANCE.ticketToTicketDto(ticketModel), HttpStatus.CREATED);
    }

    @PostMapping(value = "{code}/gain", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GainDto> assignTicketToClient(@PathVariable String code) {
        final GainDto gainDto = gainService.generateGain(code);

        return new ResponseEntity<GainDto>(gainDto, HttpStatus.OK);
    }

    @PostMapping(value = "/jackpot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> selectJackpotWinner() {
        final UserDto winner = UserMapper.INSTANCE.mapToDto(gainService.selectJackpotWinner());

        return new ResponseEntity<UserDto>(winner, HttpStatus.CREATED);
    }

}
