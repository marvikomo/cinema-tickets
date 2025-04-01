package uk.gov.dwp.uc.pairtest.validators;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;

public class PositiveQuantityValidator implements TicketValidator{
    /**
     * @param accountId
     * @param requests
     */
    @Override
    public void validate(Long accountId, TicketTypeRequest... requests) {
        boolean hasNegativeOrZeroQuantity = Arrays.stream(requests)
                .anyMatch(req -> req.getNoOfTickets() <= 0);

        if (hasNegativeOrZeroQuantity) {
            throw new InvalidPurchaseException(
                    "Ticket quantity must be positive.",
                    InvalidPurchaseException.ValidationFailureType.INVALID_TICKET_QUANTITY);
        }
    }
}
