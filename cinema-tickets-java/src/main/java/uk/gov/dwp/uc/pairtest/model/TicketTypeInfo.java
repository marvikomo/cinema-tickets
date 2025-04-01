package uk.gov.dwp.uc.pairtest.model;

public class TicketTypeInfo {
    private final int price;
    private final boolean requiresSeat;

    public TicketTypeInfo(int price, boolean requiresSeat) {
        this.price = price;
        this.requiresSeat = requiresSeat;
    }

    public int getPrice() {
        return price;
    }

    public boolean requiresSeat() {
        return requiresSeat;
    }
}
