package uk.gov.dwp.uc.pairtest.validators;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class AccountValidator implements TicketValidator{
    /**
     * @param accountId
     * @param requests
     */
    @Override
    public void validate(Long accountId, TicketTypeRequest... requests) {

        if(accountId == null || accountId <= 0) {
            throw new InvalidPurchaseException("Invalid account ID.", InvalidPurchaseException.ValidationFailureType.INVALID_ACCOUNT_ID);
        }

    }
}
