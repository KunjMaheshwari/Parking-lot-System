package service;

import java.util.*;

import domain.ParkingSlot;
import domain.Vehicle;


public class SlotService {
    private SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository){
        this.slotRepository = slotRepository;
    }

    public Optional<ParkingSlot> allocateSlot(Vehicle.VehicleType vehicleType){
        Optional<ParkingSlot> slot = slotRepository.allocateSlot(vehicleType);

        if(slot.isPresent()){
            System.out.println("Slot is allocated successfully" + slot.get().getId());
        }else{
            System.out.println("No available slots for vehicle type" + vehicleType);
        }

        return slot;
    }

    public void releaseSlot(UUID slotId){
        slotRepository.releaseSlot(slotId);
    }

    public ParkingSlot createSlot(Vehicle.VehicleType sloType, int floorNumber){
        ParkingSlot slot = new ParkingSlot(sloType, floorNumber);
        slotRepository.save(slot);

        return slot;
    }

    public long getAvaibleSlotCount(Vehicle.VehicleType vehicleType){
        return slotRepository.findAvailableSlots(vehicleType).size();
    }
}
