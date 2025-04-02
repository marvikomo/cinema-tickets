package uk.gov.dwp.uc.pairtest.validators;

import uk.gov.dwp.uc.pairtest.config.TicketServiceConfig;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.exception.PurchaseExceptionMessages;

import java.util.Arrays;

public class TicketCountValidator implements TicketValidator{
    private static int MAX_TICKETS = 25;
    /**
     * @param accountId
     * @param requests
     */
    @Override
    public void validate(Long accountId, TicketTypeRequest... requests) {
        int totalTickets = Arrays.stream(requests)
                .mapToInt(TicketTypeRequest::getNoOfTickets)
                .sum();

        if (totalTickets > TicketServiceConfig.getMaxTickets()) {
            throw new InvalidPurchaseException(
                    PurchaseExceptionMessages.format(PurchaseExceptionMessages.MSG_MAX_TICKETS_EXCEEDED, MAX_TICKETS),
                    InvalidPurchaseException.ValidationFailureType.MAX_TICKETS_EXCEEDED);
        }
    }
}
