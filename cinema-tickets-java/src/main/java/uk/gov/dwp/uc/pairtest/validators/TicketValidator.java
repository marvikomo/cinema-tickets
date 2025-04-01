package uk.gov.dwp.uc.pairtest.validators;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public interface TicketValidator {
    void validate(Long accountId, TicketTypeRequest... requests);
}
