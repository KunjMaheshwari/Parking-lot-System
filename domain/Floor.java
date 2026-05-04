package domain;

import java.util.*;

import domain.Vehicle.VehicleType;

public class Floor {
    private UUID id;
    private int floorNumber;
    private List<ParkingSlot> slots;

    public Floor(int floorNumber){
        this.floorNumber = floorNumber;
        this.id = UUID.randomUUID();
        this.slots = new ArrayList<>();
    }

    public void addSlots(ParkingSlot slot){
        slots.add(slot);
    }

    public List<ParkingSlot> getSlots(){
        return new ArrayList<>(slots);
    }

    public List<ParkingSlot> getAvailableSlots(VehicleType vehicleType){
        List<ParkingSlot> availableSlots = new ArrayList<>();
        for(ParkingSlot slot : slots){
            if(slot.getSlotType() == vehicleType && !slot.isOccupied()){
                availableSlots.add(slot);
            }
        }
        return availableSlots;
    }

    //Getter and Setters

    public UUID getId(){
        return id;
    }

    public int getFloorNumber(){
        return floorNumber;
    }

    public int getTotalSlots(){
        return slots.size();
    }

    public int getAvailableSlotsCount(Vehicle.VehicleType vehicleType){
        return getAvailableSlots(vehicleType).size();
    }

    @Override
    public String toString(){
        return "Floor{"+
                "id=" + id +
                "," + "floorNumber" + floorNumber +
                "," + "totalSlots" + slots.size() +
                "}";
    }
}
