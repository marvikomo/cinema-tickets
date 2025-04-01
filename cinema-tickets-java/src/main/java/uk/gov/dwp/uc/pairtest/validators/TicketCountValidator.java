package uk.gov.dwp.uc.pairtest.validators;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;

public class TicketCountValidator implements TicketValidator{
    /**
     * @param accountId
     * @param requests
     */
    @Override
    public void validate(Long accountId, TicketTypeRequest... requests) {
        int totalTickets = Arrays.stream(requests)
                .mapToInt(TicketTypeRequest::getNoOfTickets)
                .sum();

        if (totalTickets > 25) {
            throw new InvalidPurchaseException(
                    "Cannot purchase more than 25 tickets.",
                    InvalidPurchaseException.ValidationFailureType.MAX_TICKETS_EXCEEDED);
        }
    }
}
