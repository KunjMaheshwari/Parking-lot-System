package controller;

import domain.Vehicle;
import java.util.UUID;

public class EntryController {
    private TicketService ticketService;
    private SlotService slotService;

    public EntryController(TicketService ticketService, SlotService slotService) {
        this.ticketService = ticketService;
        this.slotService = slotService;
    }

    // method
    public EntryResult enterVehicle(String licensePlate, Vehicle.VehicleType vehicleType) {

    }

    public class EntryResult {
        private final boolean success;
        private final UUID ticketId;
        private final UUID slotId;
        private final String message;

        public EntryResult(boolean success, UUID ticketId, UUID slotId, String message) {
            this.success = success;
            this.ticketId = ticketId;
            this.slotId = slotId;
            this.message = message;
        }

        // getter and setter
        public boolean isSuccess() {
            return success;
        }

        public UUID getTicketId() {
            return ticketId;
        }

        public UUID getSlotId() {
            return slotId;
        }

        public String getMessage() {
            return message;
        }
    }
}
