package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.validators.AccountValidator;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorsTest {
    // AccountValidator tests
    @Test
    @DisplayName("AccountValidator: Should throw exception for null account ID")
    void accountValidatorShouldThrowExceptionForNullAccountId() {
        AccountValidator validator = new AccountValidator();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate(null, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)));

        assertEquals("Invalid account ID.", exception.getMessage());
    }
}
