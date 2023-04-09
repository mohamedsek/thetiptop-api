package fr.thetiptop.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.thetiptop.app.dto.GainDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/scripts/gains.sql"})
public class GainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void shouldReturnAllGains() throws Exception {

        String responseJson = this.mockMvc.perform(get("/gains")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<GainDto> gains = objectMapper.readValue(responseJson, objectMapper.getTypeFactory().constructCollectionType(List.class, GainDto.class));

        Assertions.assertEquals(4, gains.size(), "Expected numbers of gains is 4");
    }

}
