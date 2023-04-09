package fr.thetiptop.app.controller;

import fr.thetiptop.app.dto.*;
import fr.thetiptop.app.facades.TicketFacade;
import fr.thetiptop.app.mapper.TicketMapper;
import fr.thetiptop.app.mapper.UserMapper;
import fr.thetiptop.app.models.TicketModel;
import fr.thetiptop.app.service.GainService;
import fr.thetiptop.app.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private GainService gainService;

    private TicketService ticketService;

    private TicketFacade ticketFacade;

    public TicketController(GainService gainService, TicketService ticketService, TicketFacade ticketFacade) {
        this.gainService = gainService;
        this.ticketService = ticketService;
        this.ticketFacade = ticketFacade;
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketDto> createTicket() {
        final TicketModel ticketModel = ticketService.createTicket();
        return new ResponseEntity<TicketDto>(TicketMapper.INSTANCE.ticketToTicketDto(ticketModel), HttpStatus.CREATED);
    }

    @PostMapping(value = "/gain", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GainDto> assignTicketToClient(@Valid @RequestBody TicketDto ticketDto) {
        final GainDto gainDto = gainService.generateGain(ticketDto.getCode());

        return new ResponseEntity<GainDto>(gainDto, HttpStatus.OK);
    }

    @PostMapping(value = "/jackpot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> selectJackpotWinner() {
        final UserDto winner = UserMapper.INSTANCE.mapToDto(gainService.selectJackpotWinner());

        return new ResponseEntity<UserDto>(winner, HttpStatus.CREATED);
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TicketGainDto>> userGains() {

        List<TicketGainDto> ticketGains = ticketFacade.getCurrentUserGains();

        return new ResponseEntity<List<TicketGainDto>>(ticketGains, HttpStatus.OK);
    }

    @GetMapping(value = "/usergains", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TicketGainDto>> userGains(@RequestParam String email) {

        List<TicketGainDto> ticketGains = ticketFacade.getUserGains(email);

        return new ResponseEntity<List<TicketGainDto>>(ticketGains, HttpStatus.OK);
    }

    @PostMapping("/usedticket")
    public ResponseEntity<?> usedTicket(@RequestParam String usedTicketCode) {

        if (ticketService.usedTicket(usedTicketCode)) {
            return ResponseEntity.ok(ResponseDto.builder().message("ticket updated successfully").status(ResponseStatuses.Success.toString()).build());
        }
        return ResponseEntity.badRequest().body(ResponseDto.builder().message("Invalid ticket code").status(ResponseStatuses.Failure.toString()).build());
    }
}
