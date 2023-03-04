package fr.thetiptop.app.service.impl;

import fr.thetiptop.app.dto.GainDto;
import fr.thetiptop.app.mapper.GainMapper;
import fr.thetiptop.app.models.TicketModel;
import fr.thetiptop.app.service.GainService;
import fr.thetiptop.app.service.TicketService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultGainService implements GainService {

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

        return null;
    }
}
