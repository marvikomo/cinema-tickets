package uk.gov.dwp.uc.pairtest.validators;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public class NonEmptyRequestValidator implements TicketValidator{
    /**
     * @param accountId
     * @param requests
     */
    @Override
    public void validate(Long accountId, TicketTypeRequest... requests) {

    }
}
