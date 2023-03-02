package fr.thetiptop.app.service.impl;

import fr.thetiptop.app.models.TicketModel;
import fr.thetiptop.app.repository.TicketRepository;
import fr.thetiptop.app.service.TicketService;
import fr.thetiptop.app.util.GameUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DefaultTicketService implements TicketService {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${tickets.allowed.number.max}")
    private String MAX_NUMBER_OF_TICKETS;

    @Value("${game.start.date}")
    private String GAME_START_DATE;

    private TicketRepository ticketRepository;

    public DefaultTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
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
        ticketModel.setParticipating(Boolean.FALSE);
        ticketModel.setUsed(Boolean.FALSE);

        return ticketRepository.save(ticketModel);
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(generateRandomBloc());
        sb.append("-");
        sb.append(generateRandomBloc());
        sb.append("-");
        sb.append(generateRandomBloc());
        sb.append("-");
        sb.append(generateRandomBloc());

        return sb.toString();
    }

    private String generateRandomBloc() {
        int min = 1;
        int max = 9999;
        int randomNumber = GameUtil.randomValue(min, max + 1);
        return String.format("%04d", randomNumber);
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
