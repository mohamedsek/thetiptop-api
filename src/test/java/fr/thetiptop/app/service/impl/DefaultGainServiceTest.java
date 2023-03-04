package fr.thetiptop.app.service.impl;

import fr.thetiptop.app.exceptions.InvalidGameDateException;
import fr.thetiptop.app.models.TicketModel;
import fr.thetiptop.app.repository.GainRepository;
import fr.thetiptop.app.service.TicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DefaultGainServiceTest {

    @InjectMocks
    private DefaultGainService gainService;

    @Mock
    private GainRepository gainRepository;

    @Mock
    private TicketService ticketService;

    @Mock
    private TicketModel ticketModel;

    private static final String TICKET_CODE = "12345-12345";


    @Test
    public void testGenerateGain_dateInvalid() {

        Mockito.when(ticketService.findByCode(TICKET_CODE)).thenReturn(Optional.of(ticketModel));

        ReflectionTestUtils.setField(gainService, "GAME_START_DATE", "2023-01-01 00:00:00");

        Assertions.assertThrows(InvalidGameDateException.class, () -> {
            gainService.generateGain(TICKET_CODE);
        });
    }

}
