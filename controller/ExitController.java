package controller;

import java.lang.classfile.ClassFile.Option;
import java.util.*;

import domain.Receipt;
import domain.Ticket;

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
        try {
            Optional<Ticket> ticketOpt = ticketService.getTicket(ticketId);
            if (ticketOpt.isEmpty()) {
                return new ExitResult(false, null, 0.0, "Ticket not found");
            }

            Ticket ticket = ticketOpt.get();
            if (!ticket.isActive()) {
                return new ExitResult(false, null, 0.0, "Ticker is not active");
            }

            double fee = pricingService.calculateFee(ticket);

            boolean paymentSuccess = paymentService.processPaymentWithRetry(ticketId, fee, 3);

            if (!paymentSuccess) {
                return new ExitResult(false, null, fee, "Payment failed");
            }

            Receipt receipt = receiptService.generateReceipt(ticket, fee);
            receiptService.markReceiptAsPaid(receipt);

            slotService.releaseSlot(ticket.getSlotId());

            ticketService.deactiveTicket(ticketId);

            return new ExitResult(true, receipt.getId(), fee, "Exit successful");
        } catch (Exception e) {
            return new ExitResult(false, null, 0.0, e.getMessage());
        }
    }

    public String generateReceiptText(UUID ticketId){
        try{
            Optional<Ticket> ticketOpt = ticketService.getTicket(ticketId);
            if(ticketOpt.isEmpty()){
                return "Ticket not found";
            }

            Ticket ticket = ticketOpt.get();
            double fee - pricingService.calculateFee(ticket);
            Receipt receipt = receiptService.generateReceipt(ticket, fee);

            String receiptText = receiptService.generateReceipt(receipt, ticket);

            return receiptText;
        }catch(Exception e){
            return e.getMessage();
        }
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
