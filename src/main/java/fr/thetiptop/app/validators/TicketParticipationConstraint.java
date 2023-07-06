package fr.thetiptop.app.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TicketParticipationValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TicketParticipationConstraint {

    String message() default "Code ticket incorrect.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
