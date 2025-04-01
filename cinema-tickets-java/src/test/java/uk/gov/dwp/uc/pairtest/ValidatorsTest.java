package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validators.*;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorsTest {
    // AccountValidator tests
    @Test
    @DisplayName("AccountValidator: Should throw exception for null account ID")
    void accountValidatorShouldThrowExceptionForNullAccountId() {
        AccountValidator validator = new AccountValidator();
        Exception exception = assertThrows(InvalidPurchaseException.class,
                () -> validator.validate(null, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)));

        assertEquals("Invalid account ID.", exception.getMessage());
    }

    @Test
    @DisplayName("AccountValidator: Should throw exception for zero account ID")
    void accountValidatorShouldThrowExceptionForZeroAccountId() {
        AccountValidator validator = new AccountValidator();
        Exception exception = assertThrows(InvalidPurchaseException.class,
                () -> validator.validate(0L, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)));

        assertEquals("Invalid account ID.", exception.getMessage());
    }

    @Test
    @DisplayName("AccountValidator: Should throw exception for negative account ID")
    void accountValidatorShouldThrowExceptionForNegativeAccountId() {
        AccountValidator validator = new AccountValidator();
        Exception exception = assertThrows(InvalidPurchaseException.class,
                () -> validator.validate(-1L, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)));

        assertEquals("Invalid account ID.", exception.getMessage());
    }


    //Ticket Request Validtor tests

    @Test
    @DisplayName("NonEmptyRequestValidator: Should throw exception when request array is null")
    void nonEmptyRequestValidatorShouldThrowExceptionWhenRequestArrayIsNull() {
        NonEmptyRequestValidator validator = new NonEmptyRequestValidator();
        assertThrows(Exception.class, () -> validator.validate(1L, (TicketTypeRequest[]) null));
    }

    @Test
    @DisplayName("NonEmptyRequestValidator: Should throw exception with EMPTY_REQUEST when request array is empty")
    void nonEmptyRequestValidatorShouldThrowExceptionWithEmptyRequestWhenRequestArrayIsEmpty() {
        NonEmptyRequestValidator validator = new NonEmptyRequestValidator();

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class,
                () -> validator.validate(1L));

        assertEquals(InvalidPurchaseException.ValidationFailureType.EMPTY_REQUEST, exception.getFailureType());
        assertTrue(exception.getMessage().contains("cannot be empty"));
    }


    //Test for positive quantity

    @Test
    @DisplayName("PositiveQuantityValidator: Should throw exception with INVALID_TICKET_QUANTITY when zero quantity")
    void positiveQuantityValidatorShouldThrowExceptionWithInvalidTicketQuantityWhenZeroQuantity() {
        PositiveQuantityValidator validator = new PositiveQuantityValidator();
        TicketTypeRequest request1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest request2 = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 0);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class,
                () -> validator.validate(1L, request1, request2));

        assertEquals(InvalidPurchaseException.ValidationFailureType.INVALID_TICKET_QUANTITY, exception.getFailureType());
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    @DisplayName("PositiveQuantityValidator: Should accept when all ticket quantities are positive")
    void positiveQuantityValidatorShouldAcceptWhenAllTicketQuantitiesArePositive() {
        PositiveQuantityValidator validator = new PositiveQuantityValidator();
        TicketTypeRequest request1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest request2 = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2);

        assertDoesNotThrow(() -> validator.validate(1L, request1, request2));
    }

    //AdultPresence Validator teste

    @Test
    @DisplayName("AdultPresenceValidator: Should throw exception with NO_ADULT_TICKET type when only child tickets")
    void adultPresenceValidatorShouldThrowExceptionWithNoAdultTicketTypeWhenOnlyChildTickets() {
        AdultPresenceValidator validator = new AdultPresenceValidator();
        TicketTypeRequest childRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class,
                () -> validator.validate(1L, childRequest));

        assertEquals(InvalidPurchaseException.ValidationFailureType.NO_ADULT_TICKET, exception.getFailureType());
        assertTrue(exception.getMessage().contains("require at least one Adult ticket"));
    }

    @Test
    @DisplayName("AdultPresenceValidator: Should throw exception with NO_ADULT_TICKET type when only infant tickets")
    void adultPresenceValidatorShouldThrowExceptionWithNoAdultTicketTypeWhenOnlyInfantTickets() {
        AdultPresenceValidator validator = new AdultPresenceValidator();
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class,
                () -> validator.validate(1L, infantRequest));

        assertEquals(InvalidPurchaseException.ValidationFailureType.NO_ADULT_TICKET, exception.getFailureType());
        assertTrue(exception.getMessage().contains("require at least one Adult ticket"));
    }

    @Test
    @DisplayName("AdultPresenceValidator: Should throw exception with NO_ADULT_TICKET type when both infant and child tickets")
    void adultPresenceValidatorShouldThrowExceptionWithNoAdultTicketTypeWhenBothInfantAndChildTickets() {
        AdultPresenceValidator validator = new AdultPresenceValidator();
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);
        TicketTypeRequest childRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class,
                () -> validator.validate(1L, infantRequest, childRequest));

        assertEquals(InvalidPurchaseException.ValidationFailureType.NO_ADULT_TICKET, exception.getFailureType());
        assertTrue(exception.getMessage().contains("require at least one Adult ticket"));
    }

    @Test
    @DisplayName("AdultPresenceValidator: Should accept when adult ticket is present")
    void adultPresenceValidatorShouldAcceptWhenAdultTicketIsPresent() {
        AdultPresenceValidator validator = new AdultPresenceValidator();
        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);

        assertDoesNotThrow(() -> validator.validate(1L, adultRequest));
    }
    @Test
    @DisplayName("AdultPresenceValidator: Should accept when adult child and infant tickets present")
    void adultPresenceValidatorShouldAcceptWhenAdultChildAndInfantTicketsArePresent() {
        AdultPresenceValidator validator = new AdultPresenceValidator();
        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);
        TicketTypeRequest childRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        assertDoesNotThrow(() -> validator.validate(1L, adultRequest, infantRequest, childRequest));
    }


    // InfantAdultRatioValidator tests
    @Test
    @DisplayName("InfantAdultRatioValidator: Should accept when infants are fewer than adults")
    void infantAdultRatioValidatorShouldAcceptWhenInfantsAreFewerThanAdults() {
        InfantAdultRatioValidator validator = new InfantAdultRatioValidator();
        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        assertDoesNotThrow(() -> validator.validate(1L, adultRequest, infantRequest));
    }

    @Test
    @DisplayName("InfantAdultRatioValidator: Should throw exception with INFANT_ADULT_RATIO when infants exceed adults")
    void infantAdultRatioValidatorShouldThrowExceptionWithInfantAdultRatioWhenInfantsExceedAdults() {
        InfantAdultRatioValidator validator = new InfantAdultRatioValidator();
        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class,
                () -> validator.validate(1L, adultRequest, infantRequest));

        assertEquals(InvalidPurchaseException.ValidationFailureType.INFANT_ADULT_RATIO, exception.getFailureType());
        assertTrue(exception.getMessage().contains("cannot exceed number of adults"));
    }


    // TicketCountValidator tests
    @Test
    @DisplayName("TicketCountValidator: Should accept when total tickets are less than the limit")
    void ticketCountValidatorShouldAcceptWhenTotalTicketsAreLessThanLimit() {
        TicketCountValidator validator = new TicketCountValidator();
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 20);

        assertDoesNotThrow(() -> validator.validate(1L, request));
    }

    @Test
    @DisplayName("TicketCountValidator: Should throw exception with MAX_TICKETS_EXCEEDED when exceeding limit")
    void ticketCountValidatorShouldThrowExceptionWithMaxTicketsExceededWhenExceedingLimit() {
        TicketCountValidator validator = new TicketCountValidator();
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 26);

        InvalidPurchaseException exception = assertThrows(InvalidPurchaseException.class,
                () -> validator.validate(1L, request));

        assertEquals(InvalidPurchaseException.ValidationFailureType.MAX_TICKETS_EXCEEDED, exception.getFailureType());
        assertTrue(exception.getMessage().contains("more than 25 tickets"));
    }



}
