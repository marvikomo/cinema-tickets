package uk.gov.dwp.uc.pairtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validators.TicketRequestValidator;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final TicketPaymentService paymentService;

    private final SeatReservationService seatService;

    private final TicketCalculationService calculationService;

    public TicketServiceImpl(TicketPaymentService paymentService, SeatReservationService seatReservationService) {
        this.paymentService = paymentService;
        this.seatService = seatReservationService;
        this.calculationService = new TicketCalculationService();
    }

    /**
     * Purchase tickets.
     *
     * @param accountId the account ID
     * @param ticketTypeRequests the ticket requests to purchase
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        logger.info("Processing ticket purchase request for account ID: {}", accountId);
        try {
            TicketRequestValidator.validate(accountId, ticketTypeRequests);

            // Calculate the total cost and seat requirements
            int totalCost = calculationService.calculateTotalCost(ticketTypeRequests);
            int totalSeats = calculationService.calculateTotalSeats(ticketTypeRequests);

            processPayment(accountId, totalCost);
            reserveSeats(accountId, totalSeats);

            logger.info("Ticket purchase completed successfully for account ID: {}", accountId);

        }catch(InvalidPurchaseException e) {
            logger.error("Ticket purchase validation failed: {} (type: {})", e.getMessage(), e.getFailureType());
            throw e;
        }catch (Exception e) {
            logger.error("Unexpected error during ticket purchase", e);
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
            logger.info(String.format("Processing payment of £%d for account %d", totalCost, accountId));
            paymentService.makePayment(accountId, totalCost);
            logger.info(String.format("Payment processed successfully for account %d", accountId));
        } catch (Exception e) {
            logger.error("Payment processing failed", e);
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
            logger.info(String.format("Reserving %d seats for account %d", totalSeats, accountId));
            seatService.reserveSeat(accountId, totalSeats);
            logger.info(String.format("Seat reservation completed successfully for account %d", accountId));
        } catch (Exception e) {
            logger.error("Seat reservation failed", e);
            throw new InvalidPurchaseException("Seat reservation failed: " + e.getMessage(), e);
        }
    }




}
