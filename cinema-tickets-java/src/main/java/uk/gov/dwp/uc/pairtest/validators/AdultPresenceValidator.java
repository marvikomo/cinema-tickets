package uk.gov.dwp.uc.pairtest.validators;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;

public class AdultPresenceValidator implements TicketValidator{
    /**
     * @param accountId
     * @param requests
     */
    @Override
    public void validate(Long accountId, TicketTypeRequest... requests) {
        boolean hasAdult = Arrays.stream(requests)
                .anyMatch(req -> req.getTicketType() == TicketTypeRequest.Type.ADULT);

        boolean hasDependent = Arrays.stream(requests)
                .anyMatch(req -> req.getTicketType() == TicketTypeRequest.Type.CHILD ||
                        req.getTicketType() == TicketTypeRequest.Type.INFANT);

        if (!hasAdult && hasDependent) {
            throw new InvalidPurchaseException(
                    "Child or Infant tickets require at least one Adult ticket.",
                    InvalidPurchaseException.ValidationFailureType.NO_ADULT_TICKET);
        }
    }
}
