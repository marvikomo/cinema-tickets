package uk.gov.dwp.uc.pairtest.exception;

public class PurchaseExceptionMessages {
    private PurchaseExceptionMessages() {
    }

    public static final String MSG_INVALID_ACCOUNT =
            "Invalid account ID. Account ID must be greater than zero.";

    public static final String MSG_NO_TICKETS =
            "No tickets requested. At least one ticket must be requested.";

    public static final String MSG_INVALID_TICKET_COUNT =
            "Invalid ticket count. Each ticket type must have a positive count.";

    public static final String MSG_MAX_TICKETS_EXCEEDED =
            "Maximum ticket limit exceeded. You can purchase up to %d tickets at a time.";

    public static final String MSG_NO_ADULT_TICKETS =
            "No adult tickets. Child and infant tickets cannot be purchased without adult tickets.";

    public static final String MSG_TOO_MANY_INFANTS =
            "Too many infant tickets. Each infant must be accompanied by an adult. Infants: %d, Adults: %d";

    public static final String MSG_NULL_TICKET_REQUEST =
            "Null ticket request. All ticket requests must be non-null.";

    public static final String MSG_INVALID_TICKET_TYPE =
            "Invalid ticket type: %s. Valid types are ADULT, CHILD, and INFANT.";

    /**
     * Generic method for formatting any message with variable arguments
     *
     * @param messageTemplate the message template
     * @param args the arguments to format into the template
     * @return a formatted message
     */
    public static String format(String messageTemplate, Object... args) {
        return String.format(messageTemplate, args);
    }
}
