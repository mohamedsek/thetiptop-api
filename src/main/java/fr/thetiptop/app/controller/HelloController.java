package fr.thetiptop.app.controller;


import fr.thetiptop.app.dto.HelloDto;
import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.facades.UserFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Tag(name = "Hello Controller", description = "Hello Controller")
public class HelloController {

    private UserFacade userFacade;

    public HelloController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/hello")
    public HelloDto sayHello() {
        Optional<UserDto> currentUser = userFacade.getCurrentUser();
        return HelloDto.builder().greeting("Hello " + currentUser.get().getFirstName()).build();
    }
}

