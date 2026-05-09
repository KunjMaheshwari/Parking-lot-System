package controller;

import java.util.*;

import domain.PricingRule;
import domain.Vehicle;
import service.AdminService;

public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    public void initializeParkingLot(){
        adminService.initializeParkingLot();
    }

    public void addSlotsToFloor(int floorNumber, Vehicle.VehicleType sloType, int count){
        adminService.addSlotsToFloorPublic(floorNumber, sloType, count);
    }

    public void updatePricingRule(Vehicle.VehicleType vehicleType, double ratePerHour, double flatRate){
        adminService.updatePricingRule(vehicleType, ratePerHour, flatRate);
    }

    public void updateFlatPricing(Vehicle.VehicleType vehicleType, double flatRate){
        adminService.updateFlatPricing(vehicleType, flatRate);
    }

    public void updateHourlyPricing(Vehicle.VehicleType vehicleType, double ratePerHour){
        adminService.updateHourlyPricing(vehicleType, ratePerHour);
    }

    public void addPricingRule(PricingRule rule){
        adminService.addPricingRule(rule);
    }

    public Map<String, Object> getParkingStatus(){
        Map<String, Object> status = adminService.getParkingStatus();
        return status;
    }
}
