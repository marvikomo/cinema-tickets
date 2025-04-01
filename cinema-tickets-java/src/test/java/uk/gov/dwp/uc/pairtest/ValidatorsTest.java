package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validators.AccountValidator;
import uk.gov.dwp.uc.pairtest.validators.NonEmptyRequestValidator;

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

}
