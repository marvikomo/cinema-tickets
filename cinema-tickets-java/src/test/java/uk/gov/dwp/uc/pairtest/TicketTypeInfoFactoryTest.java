package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.TicketTypeInfoFactory.TicketTypeInfoFactory;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.model.TicketTypeInfo;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTypeInfoFactoryTest {
    @Test
    @DisplayName("Should return Adult ticket information")
    void shouldReturnAdultTicketInformation() {
        TicketTypeInfo info = TicketTypeInfoFactory.getInfo(TicketTypeRequest.Type.ADULT);

        assertEquals(25, info.price());
        assertTrue(info.requiresSeat());
    }

    @Test
    @DisplayName("Should return Child ticket information")
    void shouldReturnChildTicketInformation() {
        TicketTypeInfo info = TicketTypeInfoFactory.getInfo(TicketTypeRequest.Type.CHILD);

        assertEquals(15, info.price());
        assertTrue(info.requiresSeat());
    }

    @Test
    @DisplayName("Should return Infant ticket information")
    void shouldReturnInfantTicketInformation() {
        TicketTypeInfo info = TicketTypeInfoFactory.getInfo(TicketTypeRequest.Type.INFANT);

        assertEquals(0, info.price());
        assertFalse(info.requiresSeat());
    }

    @Test
    @DisplayName("Should throw exception for null ticket type")
    void shouldThrowExceptionForNullTicketType() {
        assertThrows(InvalidPurchaseException.class, () -> TicketTypeInfoFactory.getInfo(null));
    }


}
