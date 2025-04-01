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




}
