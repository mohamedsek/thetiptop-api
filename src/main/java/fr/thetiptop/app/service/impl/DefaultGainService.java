package fr.thetiptop.app.service.impl;

import fr.thetiptop.app.dto.GainDto;
import fr.thetiptop.app.mapper.GainMapper;
import fr.thetiptop.app.models.GainModel;
import fr.thetiptop.app.models.TicketModel;
import fr.thetiptop.app.service.GainService;
import fr.thetiptop.app.service.TicketService;
import fr.thetiptop.app.util.GameUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DefaultGainService implements GainService {

    @Value("${game.start.date}")
    private String GAME_START_DATE;

    private final Log logger = LogFactory.getLog(this.getClass());

    private TicketService ticketService;

    public DefaultGainService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public GainDto generateGain(String code) {
        // get ticket with code
        Optional<TicketModel> ticketModel = ticketService.findByCode(code);

        if (!ticketModel.isPresent()) {
            logger.info(String.format("Ticket code '%s' is not found.", code));
            return null;
        }

        if (ticketModel.get().getParticipating()) {
            logger.debug(String.format("Ticket code '%s' has already gain assigned returning result.", code));
            return GainMapper.INSTANCE.gainToGainDto(ticketModel.get().getGain());
        }

        logger.debug(String.format("Assigning gain to ticket code '%s'.", code));
        return GainMapper.INSTANCE.gainToGainDto(assignGain(ticketModel.get()));
    }

    private GainModel assignGain(TicketModel ticketModel) {

        if (!isGameActive()) {
            logger.warn("The game is not available at this date.");
            throw new RuntimeException("The game is not available at this date.");
        }

        return null;
    }

    private Boolean isGameActive() {
        // the duration to claim the code
        LocalDateTime gameStartDate = GameUtil.parseLocalDateTime(GAME_START_DATE);
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(gameStartDate) || now.isAfter(gameStartDate.plusMonths(2))) {
            return false;
        }

        return true;
    }
}
