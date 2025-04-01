package uk.gov.dwp.uc.pairtest.exception;

public class InvalidPurchaseException extends RuntimeException {
    /**
     * The type of validation failure that caused this exception.
     */
    private final ValidationFailureType failureType;

    /**
     * Constructs a new InvalidPurchaseException with no detail message.
     */
    public InvalidPurchaseException() {
        this.failureType = ValidationFailureType.UNKNOWN;
    }

    /**
     * Constructs a new InvalidPurchaseException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidPurchaseException(String message) {
        super(message);
        this.failureType = ValidationFailureType.UNKNOWN;
    }

    /**
     * Constructs a new InvalidPurchaseException with the specified detail message and failure type.
     *
     * @param message the detail message
     * @param failureType the type of validation failure
     */
    public InvalidPurchaseException(String message, ValidationFailureType failureType) {
        super(message);
        this.failureType = failureType;
    }

    /**
     * Constructs a new InvalidPurchaseException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public InvalidPurchaseException(String message, Throwable cause) {
        super(message, cause);
        this.failureType = ValidationFailureType.UNKNOWN;
    }

    /**
     * Constructs a new InvalidPurchaseException with the specified detail message, cause, and failure type.
     *
     * @param message the detail message
     * @param cause the cause of this exception
     * @param failureType the type of validation failure
     */
    public InvalidPurchaseException(String message, Throwable cause, ValidationFailureType failureType) {
        super(message, cause);
        this.failureType = failureType;
    }

    /**
     * Returns the type of validation failure that caused this exception.
     *
     * @return the failure type
     */
    public ValidationFailureType getFailureType() {
        return failureType;
    }

    /**
     * Enum representing the different types of validation failures that can occur.
     */
    public enum ValidationFailureType {
        INVALID_ACCOUNT_ID("Account ID validation failed"),
        EMPTY_REQUEST("No ticket requests provided"),
        INVALID_TICKET_QUANTITY("Invalid ticket quantity"),
        MAX_TICKETS_EXCEEDED("Maximum number of tickets exceeded"),
        NO_ADULT_TICKET("Adult ticket required"),
        INFANT_ADULT_RATIO("Too many infants per adult"),
        PAYMENT_FAILED("Payment processing failed"),
        SEAT_RESERVATION_FAILED("Seat reservation failed"),
        UNKNOWN("Unknown validation failure");

        private final String description;

        ValidationFailureType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
