package fr.thetiptop.app.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.thetiptop.app.dto.auth.AuthResponseDto;
import fr.thetiptop.app.dto.auth.SignInRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@Sql(value = {"/scripts/users.sql", "/scripts/gains.sql", "/scripts/tickets.sql"})
public class BaseControllerTest {

    protected static final String CUSTOMER_USERNAME = "customer@domain.com";
    protected static final String USER_PASSWORD = "azertyazerty";

    protected static final String VALID_TICKET_CODE = "12345-12345";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected String obtainAccessToken(String username, String password) throws Exception {

        SignInRequestDto signInRequestDto = new SignInRequestDto(username, password);

        String result = mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(signInRequestDto)).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        AuthResponseDto authResponseDto = objectMapper.readValue(result, AuthResponseDto.class);

        return "Bearer " + authResponseDto.getAccessToken();
    }

}