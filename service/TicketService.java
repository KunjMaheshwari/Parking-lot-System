package service;

import domain.Ticket;
import domain.Vehicle;
import java.util.*;
import repository.*;

public class TicketService {
    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    public Ticket generateTicket(Vehicle vehicle, UUID slotId){
        System.out.println("Generating the ticket");

        Ticket ticket = new Ticket(vehicle.getId(), slotId);
        ticketRepository.save(ticket);

        System.out.println("Ticket generated successfully");
        return ticket;
    }

    public Optional<Ticket> getTicket(UUID ticketId){
        return ticketRepository.findById(ticketId);
    }

    public void deactiveTicket(UUID ticketId){
        ticketRepository.deactivateTicket(ticketId);
    }
}
