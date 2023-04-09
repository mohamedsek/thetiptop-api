package fr.thetiptop.app.controller;

import fr.thetiptop.app.dto.GainDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class GainControllerTest extends BaseControllerTest {

    @Test
    public void shouldReturnAllGains() throws Exception {

        String responseJson = this.mockMvc.perform(get("/gains")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<GainDto> gains = objectMapper.readValue(responseJson, objectMapper.getTypeFactory().constructCollectionType(List.class, GainDto.class));

        Assertions.assertEquals(4, gains.size(), "Expected numbers of gains is 4");
    }

}
