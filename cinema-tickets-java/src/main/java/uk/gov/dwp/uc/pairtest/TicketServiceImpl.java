package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */

    private final TicketPaymentService paymentService;

    private final SeatReservationService seatService;

    public TicketServiceImpl(TicketPaymentService paymentService, SeatReservationService seatReservationService) {
        this.paymentService = paymentService;
        this.seatService = seatReservationService;
    }

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        if (accountId == null || accountId <= 0) {
            throw new InvalidPurchaseException(
                    "Invalid account ID.",
                    InvalidPurchaseException.ValidationFailureType.INVALID_ACCOUNT_ID);
        }
        if (ticketTypeRequests == null || ticketTypeRequests.length == 0) {
            throw new InvalidPurchaseException(
                    "Ticket requests cannot be empty.",
                    InvalidPurchaseException.ValidationFailureType.EMPTY_REQUEST);
        }
    }

}
