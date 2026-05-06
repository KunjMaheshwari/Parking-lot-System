package controller;

import java.util.*;

public class ExitController {
    private TicketService ticketService;
    private PricingService pricingService;
    private PaymentService paymentService;
    private ReceiptService receiptService;
    private SlotService slotService;

    public ExitController(TicketService ticketService, PricingService pricingService,
            PaymentService paymentService, ReceiptService receiptService,
            SlotService slotService) {
        this.ticketService = ticketService;
        this.pricingService = pricingService;
        this.paymentService = paymentService;
        this.receiptService = receiptService;
        this.slotService = slotService;
    }

    public ExitResult exitVehicle(UUID ticketId) {

    }

    public class ExitResult {
        private final boolean success;
        private final UUID ticketId;
        private final double fee;
        private final String message;

        // we are using constructor to initialize the required above dependencies.
        // we are not creating object every time because it is bad practice, tightly
        // coupled, difficult to test
        // and violates the dependency injection principle.

        public ExitResult(boolean success, UUID ticketId, double fee, String message) {
            this.success = success;
            this.ticketId = ticketId;
            this.fee = fee;
            this.message = message;
        }

        // getter and setter method

        // here why are we using getter and setter because of encapsulation.
        // instead of result.success
        // i will call result.isSuccess()
        public boolean isSuccess() {
            return success;
        }

        public UUID getTicketId() {
            return ticketId;
        }

        public double getFee() {
            return fee;
        }

        public String getMessage() {
            return message;
        }

        /*
         * But Modern Backend Design Often Avoids Setters
         * 
         * In scalable backend systems:
         * 
         * immutable objects are preferred
         * constructors initialize values
         * only getters are exposed
         * 
         * Why?
         * 
         * Because mutable state causes:
         * 
         * thread safety issues
         * race conditions
         * inconsistent state
         */
    }
}
