package controller;

import domain.Ticket;
import domain.Vehicle;
import java.util.*;

public class EntryController {
    private TicketService ticketService;
    private SlotService slotService;

    public EntryController(TicketService ticketService, SlotService slotService) {
        this.ticketService = ticketService;
        this.slotService = slotService;
    }

    // method
    public EntryResult enterVehicle(String licensePlate, Vehicle.VehicleType vehicleType) {
        try {
            Vehicle vehicle = new Vehicle(licensePlate, vehicleType);

            Optional<UUID> slotId = slotService.allocateSlot(vehicleType).map(slot -> slot.getId());

            if (slotId.isEmpty()) {
                return new EntryResult(false, null, null, "No available slots for vehicle type" + vehicleType);
            }

            Ticket ticket = ticketService.generateTicket(vehicle, slotId.get());

            return new EntryResult(true, ticket.getId(), slotId.get(), "Entry Successful");
        } catch (Exception e) {
            return new EntryResult(false, null, null, e.getMessage());
        }
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
