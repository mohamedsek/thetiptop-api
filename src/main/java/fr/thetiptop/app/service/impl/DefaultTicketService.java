package fr.thetiptop.app.service.impl;

import fr.thetiptop.app.models.ClientModel;
import fr.thetiptop.app.models.TicketModel;
import fr.thetiptop.app.repository.TicketRepository;
import fr.thetiptop.app.service.ClientService;
import fr.thetiptop.app.service.TicketService;
import fr.thetiptop.app.util.GameUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultTicketService implements TicketService {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${tickets.allowed.number.max}")
    private String MAX_NUMBER_OF_TICKETS;

    @Value("${game.start.date}")
    private String GAME_START_DATE;

    private TicketRepository ticketRepository;

    private ClientService clientService;

    public DefaultTicketService(TicketRepository ticketRepository, ClientService clientService) {
        this.ticketRepository = ticketRepository;
        this.clientService = clientService;
    }

    @Override
    public TicketModel createTicket() {

        if (!canGenerateTicket()) {
            throw new RuntimeException("Cannot create ticket at this date");
        }

        if (ticketRepository.count() >= Long.valueOf(MAX_NUMBER_OF_TICKETS)) {
            throw new RuntimeException("number of tickets reached the maximum value");
        }

        TicketModel ticketModel = new TicketModel();
        ticketModel.setCode(generateCode());
        ticketModel.setIsParticipating(Boolean.FALSE);
        ticketModel.setIsUsed(Boolean.FALSE);

        return ticketRepository.save(ticketModel);
    }

    @Override
    public Optional<TicketModel> findByCode(String ticketCode) {
        Optional<TicketModel> ticketModel = ticketRepository.findByCode(ticketCode);

        return ticketModel;
    }

    @Override
    public TicketModel save(TicketModel ticketModel) {
        return ticketRepository.save(ticketModel);
    }

    @Override
    public List<TicketModel> getCurrentUserGains() {
        ClientModel currentCustomer = clientService.getCurrentCustomer();
        List<TicketModel> userTickets = ticketRepository.findByClient(currentCustomer);
        return userTickets;
    }

    public List<TicketModel> getUserGains(String email) {
        ClientModel customer = clientService.findByEmail(email);
        List<TicketModel> userTickets = ticketRepository.findByClient(customer);
        return userTickets;
    }
    public boolean usedTicket(String code){
        Optional<TicketModel> ticket = ticketRepository.findByCode(code);
        if (ticket.isPresent()){
            ticket.get().setIsUsed(true);
            ticketRepository.save(ticket.get());
            return true;
        }
        return false;
    }

    public void removeJackpotTicket() {
        Optional<TicketModel> jackpotTicket = this.findByCode(JACKPOT_TICKET_CODE);
        if (jackpotTicket.isPresent()) {
            logger.info(String.format("Clearing previous jackpot winner ticket '%s' assigned to '%s'", jackpotTicket.get().getCode(), jackpotTicket.get().getClient().getEmail()));
            ticketRepository.delete(jackpotTicket.get());
        }
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(generateRandomBloc());
        sb.append("-");
        sb.append(generateRandomBloc());

        return sb.toString();
    }

    private String generateRandomBloc() {
        int min = 1;
        int max = 99999;
        int randomNumber = GameUtil.randomValue(min, max + 1);
        return String.format("%05d", randomNumber);
    }

    private Boolean canGenerateTicket() {
        LocalDateTime gameStartDate = GameUtil.parseLocalDateTime(GAME_START_DATE);
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(gameStartDate) || now.isAfter(gameStartDate.plusMonths(1))) {
            return false;
        }

        return true;
    }
}
