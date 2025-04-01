package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;

public class TicketServiceImplTest {

    @Mock
    private TicketPaymentService paymentService;

    @Mock
    private SeatReservationService seatService;

    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        paymentService = Mockito.mock(TicketPaymentService.class);
        seatService = Mockito.mock(SeatReservationService.class);
        ticketService = new TicketServiceImpl(paymentService, seatService);
    }

    @Test
    @DisplayName("Should throw exception when both account ID is null and request is empty")
    void shouldThrowExceptionWhenBothAccountIdNullAndRequestEmpty() {
        // Both null account ID and empty request array
        Exception exception = assertThrows(InvalidPurchaseException.class,
                () -> ticketService.purchaseTickets(null));
    }

    @Test
    @DisplayName("Should throw exception with INVALID_ACCOUNT_ID type when account ID is null")
    void shouldThrowExceptionWithInvalidAccountIdTypeWhenAccountIdIsNull() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(null, request));

        assertEquals(InvalidPurchaseException.ValidationFailureType.INVALID_ACCOUNT_ID, exception.getFailureType());
        assertTrue(exception.getMessage().contains("Invalid account ID"));
    }

    @Test
    @DisplayName("Should throw exception with INVALID_ACCOUNT_ID type when account ID is zero")
    void shouldThrowExceptionWithInvalidAccountIdTypeWhenAccountIdIsZero() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(0L, request));

        assertEquals(InvalidPurchaseException.ValidationFailureType.INVALID_ACCOUNT_ID, exception.getFailureType());
        assertTrue(exception.getMessage().contains("Invalid account ID"));
    }

    @Test
    @DisplayName("Should throw exception with INVALID_ACCOUNT_ID type when account ID is negative")
    void shouldThrowExceptionWithInvalidAccountIdTypeWhenAccountIdIsNegative() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(-1L, request));

        assertEquals(InvalidPurchaseException.ValidationFailureType.INVALID_ACCOUNT_ID, exception.getFailureType());
        assertTrue(exception.getMessage().contains("Invalid account ID"));
    }


    @Test
    @DisplayName("Should throw exception with EMPTY_REQUEST type when ticket requests is null")
    void shouldThrowExceptionWithEmptyRequestTypeWhenTicketRequestsIsNull() {
        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(1L, (TicketTypeRequest[]) null));

        assertEquals(InvalidPurchaseException.ValidationFailureType.EMPTY_REQUEST, exception.getFailureType());
        assertTrue(exception.getMessage().contains("cannot be empty"));
    }

    @Test
    @DisplayName("Should throw exception with EMPTY_REQUEST type when ticket requests is empty")
    void shouldThrowExceptionWithEmptyRequestTypeWhenTicketRequestsIsEmpty() {
        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(1L));

        assertEquals(InvalidPurchaseException.ValidationFailureType.EMPTY_REQUEST, exception.getFailureType());
        assertTrue(exception.getMessage().contains("cannot be empty"));
    }

    @Test
    @DisplayName("Should throw exception with INVALID_TICKET_QUANTITY type when quantity is zero")
    void shouldThrowExceptionWithInvalidTicketQuantityTypeWhenQuantityIsZero() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 0);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(1L, request));

        assertEquals(InvalidPurchaseException.ValidationFailureType.INVALID_TICKET_QUANTITY, exception.getFailureType());
        assertTrue(exception.getMessage().contains("quantity must be positive"));
    }

    @Test
    @DisplayName("Should throw exception with INVALID_TICKET_QUANTITY type when quantity is negative")
    void shouldThrowExceptionWithInvalidTicketQuantityTypeWhenQuantityIsNegative() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, -1);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(1L, request));

        assertEquals(InvalidPurchaseException.ValidationFailureType.INVALID_TICKET_QUANTITY, exception.getFailureType());
        assertTrue(exception.getMessage().contains("quantity must be positive"));
    }

    @Test
    @DisplayName("Should throw exception with MAX_TICKETS_EXCEEDED type when total exceeds 25")
    void shouldThrowExceptionWithMaxTicketsExceededTypeWhenTotalExceeds25() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 26);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(1L, request));

        assertEquals(InvalidPurchaseException.ValidationFailureType.MAX_TICKETS_EXCEEDED, exception.getFailureType());
        assertTrue(exception.getMessage().contains("more than 25 tickets"));
    }

    @Test
    @DisplayName("Should throw exception with NO_ADULT_TICKET type when child tickets without adult")
    void shouldThrowExceptionWithNoAdultTicketTypeWhenChildTicketsWithoutAdult() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(1L, request));

        assertEquals(InvalidPurchaseException.ValidationFailureType.NO_ADULT_TICKET, exception.getFailureType());
        assertTrue(exception.getMessage().contains("require at least one Adult ticket"));
    }

    @Test
    @DisplayName("Should throw exception with NO_ADULT_TICKET type when infant tickets without adult")
    void shouldThrowExceptionWithNoAdultTicketTypeWhenInfantTicketsWithoutAdult() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(1L, request));

        assertEquals(InvalidPurchaseException.ValidationFailureType.NO_ADULT_TICKET, exception.getFailureType());
        assertTrue(exception.getMessage().contains("require at least one Adult ticket"));
    }

    @Test
    @DisplayName("Should throw exception with INFANT_ADULT_RATIO type when infants exceed adults")
    void shouldThrowExceptionWithInfantAdultRatioTypeWhenInfantsExceedAdults() {
        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(1L, adultRequest, infantRequest));

        assertEquals(InvalidPurchaseException.ValidationFailureType.INFANT_ADULT_RATIO, exception.getFailureType());
        assertTrue(exception.getMessage().contains("cannot exceed number of adults"));
    }





}
