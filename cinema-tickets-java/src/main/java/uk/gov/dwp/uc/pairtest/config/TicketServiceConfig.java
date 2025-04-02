package uk.gov.dwp.uc.pairtest.config;

public class TicketServiceConfig {
    // Ticket price constants
    public static final int ADULT_TICKET_PRICE = 25;
    public static final int CHILD_TICKET_PRICE = 15;
    public static final int INFANT_TICKET_PRICE = 0;

    // Seat requirement constants
    public static final boolean ADULT_REQUIRES_SEAT = true;
    public static final boolean CHILD_REQUIRES_SEAT = true;
    public static final boolean INFANT_REQUIRES_SEAT = false;

    // Default maximum number of tickets per purchase
    private static final int DEFAULT_MAX_TICKETS = 25;

    // Current maximum number of tickets that can be purchased
    private static int maxTickets = DEFAULT_MAX_TICKETS;

    /**
     * Gets the maximum number of tickets allowed per purchase.
     *
     * @return the maximum number of tickets
     */
    public static int getMaxTickets() {
        return maxTickets;
    }

    /**
     * Sets the maximum number of tickets allowed per purchase.
     *
     * @param newMaxTickets the new maximum number of tickets
     * @throws IllegalArgumentException if newMaxTickets is less than or equal to zero
     */
    public static void setMaxTickets(int newMaxTickets) {
        if (newMaxTickets <= 0) {
            throw new IllegalArgumentException("Maximum ticket count must be greater than zero");
        }
        maxTickets = newMaxTickets;
    }

    /**
     * Resets the maximum number of tickets to the default value.
     */
    public static void resetMaxTicketsToDefault() {
        maxTickets = DEFAULT_MAX_TICKETS;
    }
}
