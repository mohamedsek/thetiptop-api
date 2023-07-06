package fr.thetiptop.app.validators;

import fr.thetiptop.app.models.ClientModel;
import fr.thetiptop.app.models.TicketModel;
import fr.thetiptop.app.service.ClientService;
import fr.thetiptop.app.service.TicketService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.Optional;

public class TicketParticipationValidator implements ConstraintValidator<TicketParticipationConstraint, String> {

    private TicketService ticketService;
    private ClientService clientService;

    public TicketParticipationValidator(TicketService ticketService, ClientService clientService) {
        this.ticketService = ticketService;
        this.clientService = clientService;
    }

    @Override
    public void initialize(TicketParticipationConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String ticketCode, ConstraintValidatorContext constraintValidatorContext) {
        Optional<TicketModel> ticket = ticketService.findByCode(ticketCode);
        // Ticket Code not FOUND
        if(ticket.isEmpty()) {
            return false;
        }
        ClientModel currentCustomer = clientService.getCurrentCustomer();
        // Ticket Belong to another customer
        if(Objects.nonNull(currentCustomer)
                && Objects.nonNull(ticket.get().getClient())
                && !ticket.get().getClient().getId().equals(currentCustomer.getId()) ) {
            return false;
        }

        return true;
    }
}
