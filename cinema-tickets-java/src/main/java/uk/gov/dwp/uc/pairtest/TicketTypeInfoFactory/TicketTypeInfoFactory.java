package uk.gov.dwp.uc.pairtest.TicketTypeInfoFactory;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.model.TicketTypeInfo;

import java.util.Map;

public class TicketTypeInfoFactory {
    private static final Map<TicketTypeRequest.Type, TicketTypeInfo> TICKET_CONFIG = Map.of(
            TicketTypeRequest.Type.ADULT, new TicketTypeInfo(25, true),
            TicketTypeRequest.Type.CHILD, new TicketTypeInfo(15, true),
            TicketTypeRequest.Type.INFANT, new TicketTypeInfo(0, false)
    );

    /**
     * Get the ticket information for a specific ticket type.
     *
     * @param type the ticket type
     * @return the corresponding TicketTypeInfo object
     * @throws IllegalArgumentException if the ticket type is not supported
     */
    public static TicketTypeInfo getInfo(TicketTypeRequest.Type type) {
        try {

            return TICKET_CONFIG.get(type);
        }catch (NullPointerException exception) {
            throw new  InvalidPurchaseException(exception.getMessage());
        }
    }

}
