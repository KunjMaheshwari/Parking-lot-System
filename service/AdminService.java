package service;

import domain.Floor;
import domain.ParkingSlot;
import domain.PricingRule;
import domain.Vehicle;
import repository.FloorRepository;
import repository.PricingRuleRepository;
import repository.SlotRepository;

import java.util.List;
import java.util.Map;

public class AdminService {

    private FloorRepository floorRepository;
    private SlotRepository slotRepository;
    private PricingRuleRepository pricingRuleRepository;

    public AdminService(FloorRepository floorRepository,
                        SlotRepository slotRepository,
                        PricingRuleRepository pricingRuleRepository) {

        this.floorRepository = floorRepository;
        this.slotRepository = slotRepository;
        this.pricingRuleRepository = pricingRuleRepository;
    }

    public void initializeParkingLot() {

        // Create 3 floors
        for (int i = 0; i < 3; i++) {
            addFloor(i);
        }

        // Add slots to floor 0
        addSlotsToFloor(0, Vehicle.VehicleType.BIKE, 20);
        addSlotsToFloor(0, Vehicle.VehicleType.CAR, 30);
        addSlotsToFloor(0, Vehicle.VehicleType.TRUCK, 5);

        // Add slots to floor 1
        addSlotsToFloor(1, Vehicle.VehicleType.CAR, 40);
        addSlotsToFloor(1, Vehicle.VehicleType.EV, 10);

        // Add slots to floor 2
        addSlotsToFloor(2, Vehicle.VehicleType.CAR, 35);
        addSlotsToFloor(2, Vehicle.VehicleType.EV, 15);

        // Initialize default pricing rules
        initializeDefaultPricingRules();
    }

    private void addFloor(int floorNumber) {

        if (floorRepository.existsByNumber(floorNumber)) {
            return;
        }

        Floor floor = new Floor(floorNumber);
        floorRepository.save(floor);
    }

    private void addSlotsToFloor(int floorNumber,
                                 Vehicle.VehicleType slotType,
                                 int count) {

        Floor floor = floorRepository.findByNumber(floorNumber)
                .orElseThrow(() ->
                        new IllegalStateException("Floor " + floorNumber + " not found"));

        for (int i = 0; i < count; i++) {

            ParkingSlot slot = new ParkingSlot(slotType, floorNumber);

            slotRepository.save(slot);

            floor.addSlot(slot);
        }
    }

    private void initializeDefaultPricingRules() {

        PricingRule bikeRule =
                new PricingRule(Vehicle.VehicleType.BIKE, 10.0, 30.0);

        PricingRule carRule =
                new PricingRule(Vehicle.VehicleType.CAR, 20.0, 60.0);

        PricingRule truckRule =
                new PricingRule(Vehicle.VehicleType.TRUCK, 30.0, 90.0);

        PricingRule evRule =
                new PricingRule(Vehicle.VehicleType.EV, 15.0, 45.0);

        pricingRuleRepository.save(bikeRule);
        pricingRuleRepository.save(carRule);
        pricingRuleRepository.save(truckRule);
        pricingRuleRepository.save(evRule);
    }

    public void addFloorPublic(int floorNumber) {
        addFloor(floorNumber);
    }

    public void addSlotsToFloorPublic(int floorNumber,
                                      Vehicle.VehicleType slotType,
                                      int count) {

        addSlotsToFloor(floorNumber, slotType, count);
    }

    public void updatePricingRule(Vehicle.VehicleType vehicleType,
                                  double ratePerHour,
                                  double flatRate) {

        PricingRule rule = pricingRuleRepository
                .findByVehicleType(vehicleType)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Pricing rule not found for " + vehicleType));

        rule.updateRates(ratePerHour, flatRate);

        pricingRuleRepository.update(rule);
    }

    public void updateFlatPricing(Vehicle.VehicleType vehicleType,
                                  double flatRate) {

        PricingRule rule = pricingRuleRepository
                .findByVehicleType(vehicleType)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Pricing rule not found for " + vehicleType));

        rule.updateFlatRate(flatRate);

        pricingRuleRepository.update(rule);
    }

    public void updateHourlyPricing(Vehicle.VehicleType vehicleType,
                                    double ratePerHour) {

        PricingRule rule = pricingRuleRepository
                .findByVehicleType(vehicleType)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Pricing rule not found for " + vehicleType));

        rule.updateHourlyRate(ratePerHour);

        pricingRuleRepository.update(rule);
    }

    public void addPricingRule(PricingRule rule) {

        pricingRuleRepository.save(rule);
    }

    public Map<String, Object> getParkingStatus() {

        List<Floor> floors = floorRepository.findAll();

        Map<Vehicle.VehicleType, Long> slotStats =
                slotRepository.getSlotStatistics();

        return Map.of(
                "totalFloors", floors.size(),
                "slotStatistics", slotStats
        );
    }
}