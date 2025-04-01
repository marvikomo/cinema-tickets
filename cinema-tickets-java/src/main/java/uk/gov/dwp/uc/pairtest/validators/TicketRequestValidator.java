package uk.gov.dwp.uc.pairtest.validators;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.List;

public class TicketRequestValidator{
    private static final List<TicketValidator> VALIDATORS = List.of(
            new NonEmptyRequestValidator(),
            new AccountValidator(),
            new PositiveQuantityValidator(),
            new TicketCountValidator(),
            new AdultPresenceValidator(),
            new InfantAdultRatioValidator()
    );

    public static void validate(Long accountId, TicketTypeRequest... requests) {
        for (TicketValidator validator : VALIDATORS) {
            validator.validate(accountId, requests);
        }
    }
}
