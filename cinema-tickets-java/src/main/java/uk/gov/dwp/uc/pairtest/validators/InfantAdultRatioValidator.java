package uk.gov.dwp.uc.pairtest.validators;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class InfantAdultRatioValidator implements TicketValidator{

    /**
     * @param accountId
     * @param requests
     */
    @Override
    public void validate(Long accountId, TicketTypeRequest... requests) {
        int adultCount = 0;
        int infantCount = 0;

        for (TicketTypeRequest request : requests) {
            if (request.getTicketType() == TicketTypeRequest.Type.ADULT) {
                adultCount += request.getNoOfTickets();
            } else if (request.getTicketType() == TicketTypeRequest.Type.INFANT) {
                infantCount += request.getNoOfTickets();
            }
        }

        if (infantCount > adultCount) {
            throw new InvalidPurchaseException(
                    "Number of infants cannot exceed number of adults.",
                    InvalidPurchaseException.ValidationFailureType.INFANT_ADULT_RATIO);
        }

    }
}
