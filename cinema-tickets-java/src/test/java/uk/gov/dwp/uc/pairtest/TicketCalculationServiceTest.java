package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.TicketTypeInfoFactory.TicketTypeInfoFactory;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketCalculationServiceTest {
    private TicketCalculationService calculationService;

    int adultPrice = TicketTypeInfoFactory.getInfo(TicketTypeRequest.Type.ADULT).getPrice();
    int childPrice =  TicketTypeInfoFactory.getInfo(TicketTypeRequest.Type.CHILD).getPrice();

    @BeforeEach
    void setUp() {
        calculationService = new TicketCalculationService();
    }

    @Test
    @DisplayName("Should calculate total cost correctly for adult tickets")
    void shouldCalculateTotalCostForAdultTickets() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3);

        int totalCost = calculationService.calculateTotalCost(request);

        assertEquals(adultPrice * 3, totalCost);
    }

    @Test
    @DisplayName("Should calculate total cost correctly for child tickets")
    void shouldCalculateTotalCostForChildTickets() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4);

        int totalCost = calculationService.calculateTotalCost(request);

        assertEquals(childPrice * 4, totalCost);
    }

    @Test
    @DisplayName("Should calculate total cost correctly for infant tickets")
    void shouldCalculateTotalCostForInfantTickets() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2);

        int totalCost = calculationService.calculateTotalCost(request);

        assertEquals(0, totalCost);
    }

    @Test
    @DisplayName("Should calculate total cost correctly for mixed ticket types")
    void shouldCalculateTotalCostForMixedTicketTypes() {
        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest childRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3);
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        int totalCost = calculationService.calculateTotalCost(adultRequest, childRequest, infantRequest);

        assertEquals((adultPrice * 2 + childPrice * 3), totalCost);
    }

    @Test
    @DisplayName("Should calculate total seats correctly for child tickets")
    void shouldCalculateTotalSeatsForChildTickets() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4);

        int totalSeats = calculationService.calculateTotalSeats(request);

        assertEquals(4, totalSeats); // 4 children require 4 seats
    }

    @Test
    @DisplayName("Should calculate total seats correctly for infant tickets")
    void shouldCalculateTotalSeatsForInfantTickets() {
        TicketTypeRequest request = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2);

        int totalSeats = calculationService.calculateTotalSeats(request);

        assertEquals(0, totalSeats); // 2 infants require 0 seats
    }


    @Test
    @DisplayName("Should calculate total seats correctly for mixed ticket types")
    void shouldCalculateTotalSeatsForMixedTicketTypes() {
        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest childRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3);
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        int totalSeats = calculationService.calculateTotalSeats(adultRequest, childRequest, infantRequest);

        assertEquals(5, totalSeats); // 2 adults + 3 children + 0 infants = 5 seats
    }

    @Test
    @DisplayName("Should handle empty array when calculating total cost")
    void shouldHandleEmptyArrayWhenCalculatingTotalCost() {
        int totalCost = calculationService.calculateTotalCost();

        assertEquals(0, totalCost);
    }

    @Test
    @DisplayName("Should handle empty array when calculating total seats")
    void shouldHandleEmptyArrayWhenCalculatingTotalSeats() {
        int totalSeats = calculationService.calculateTotalSeats();

        assertEquals(0, totalSeats);
    }




}
