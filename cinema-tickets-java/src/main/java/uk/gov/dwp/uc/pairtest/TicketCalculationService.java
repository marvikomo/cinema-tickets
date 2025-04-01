package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.TicketTypeInfoFactory.TicketTypeInfoFactory;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.model.TicketTypeInfo;

public class TicketCalculationService {
    /**
     * Calculate the total cost of the requested tickets.
     *
     * @param ticketTypeRequests the ticket requests
     * @return the total cost
     */
    public int calculateTotalCost(TicketTypeRequest... ticketTypeRequests) {
        int totalCost = 0;

        for (TicketTypeRequest req : ticketTypeRequests) {
            TicketTypeRequest.Type type = req.getTicketType();
            TicketTypeInfo info = TicketTypeInfoFactory.getInfo(type);

            int quantity = req.getNoOfTickets();
            totalCost += quantity * info.price();
        }

        return totalCost;
    }

    /**
     * Calculate the total number of seats needed for the requested tickets.
     *
     * @param ticketTypeRequests the ticket requests
     * @return the total number of seats
     */
    public int calculateTotalSeats(TicketTypeRequest... ticketTypeRequests) {
        int totalSeats = 0;

        for (TicketTypeRequest req : ticketTypeRequests) {
            TicketTypeRequest.Type type = req.getTicketType();
            TicketTypeInfo info = TicketTypeInfoFactory.getInfo(type);

            int quantity = req.getNoOfTickets();
            if (info.requiresSeat()) {
                totalSeats += quantity;
            }
        }

        return totalSeats;
    }


}
