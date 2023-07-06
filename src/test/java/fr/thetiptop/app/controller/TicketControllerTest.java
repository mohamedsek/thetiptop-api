package fr.thetiptop.app.controller;

import fr.thetiptop.app.dto.GainDto;
import fr.thetiptop.app.dto.TicketDto;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TicketControllerTest extends BaseControllerTest {

    @Test
    public void testParticipateValidTicket() throws Exception {

        String customerToken = this.obtainAccessToken(CUSTOMER_USERNAME, USER_PASSWORD);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setCode(VALID_TICKET_CODE);

        String responseJson = this.mockMvc.perform(post("/tickets/gain")
                        .header("Authorization", customerToken)
                        .content(objectMapper.writeValueAsString(ticketDto))
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        GainDto gainDto = objectMapper.readValue(responseJson, GainDto.class);

        Assertions.assertNotNull(gainDto, "Should assign a gain to ticket");

        String gainTitle = gainDto.getTitle();

        Assertions.assertFalse(StringUtils.equalsIgnoreCase(gainTitle, "test -title jackpot"), "Should not assign Jackpot");
    }

}
