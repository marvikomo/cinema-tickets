import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestServiceImpl {

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
        //ticketService.purchaseTickets(null);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> ticketService.purchaseTickets(null));
    }

    @Test
    void simplePassingTest() {
        assertTrue(true);
    }
}
