package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validators.TicketRequestValidator;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */

    private final TicketPaymentService paymentService;

    private final SeatReservationService seatService;

    private final TicketCalculationService calculationService;

    public TicketServiceImpl(TicketPaymentService paymentService, SeatReservationService seatReservationService) {
        this.paymentService = paymentService;
        this.seatService = seatReservationService;
        this.calculationService = new TicketCalculationService();
    }

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        try {
            TicketRequestValidator.validate(accountId, ticketTypeRequests);

            // Calculate the total cost and seat requirements
            int totalCost = calculationService.calculateTotalCost(ticketTypeRequests);
            int totalSeats = calculationService.calculateTotalSeats(ticketTypeRequests);

            processPayment(accountId, totalCost);
            reserveSeats(accountId, totalSeats);

        }catch(InvalidPurchaseException e) {
            throw e;
        }catch (Exception e) {
            throw new InvalidPurchaseException("Unexpected error during ticket purchase: " + e.getMessage(), e);
        }

    }

    /**
     * Process the payment for the tickets.
     *
     * @param accountId the account ID
     * @param totalCost the total cost
     */
    private void processPayment(long accountId, int totalCost) {
        try {
            paymentService.makePayment(accountId, totalCost);
        } catch (Exception e) {
           throw new InvalidPurchaseException("Payment processing failed: " + e.getMessage(), e);
        }
    }

    /**
     * Reserve seats for the tickets.
     *
     * @param accountId the account ID
     * @param totalSeats the total number of seats
     */
    private void reserveSeats(long accountId, int totalSeats) {
        try {
            seatService.reserveSeat(accountId, totalSeats);
        } catch (Exception e) {
          throw new InvalidPurchaseException("Seat reservation failed: " + e.getMessage(), e);
        }
    }




}
