package service;

import java.util.*;
import domain.*;
import repository.*;

public class PricingService {
    private PricingRuleRepository pricingRuleRepository;

    public PricingService(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }

    public double calculateFee(Ticket ticket) {
        Vehicle.VehicleType vehicleType = Vehicle.VehicleType.CAR;

        // we are using optional to present the null pointer exception if the pricing
        // rule is empty.
        Optional<PricingRule> rule = pricingRuleRepository.findByVehicleType(vehicleType);

        if (rule.isEmpty()) {
            throw new IllegalStateException("No pricing rule found for vehicle type: " + vehicleType);
        }

        PricingRule pricingRule = rule.get();

        double flatFee = pricingRule.getFlatRate();
        double hourlyFee = calculateHourlyFee(ticket, pricingRule.getRatePerHour());

        double finalFee = Math.min(flatFee, hourlyFee);

        return finalFee;
    }

    public double calculateHourlyFee(Ticket ticket, double ratePerHour) {
        java.time.Duration duration = java.time.Duration.between(ticket.getEntryTime(), java.time.LocalDateTime.now());

        long hours = duration.toHours();

        if (hours < 1) {
            hours = 1;
        }

        return hours * ratePerHour;
    }

    public void addPricingRule(PricingRule rule) {
        pricingRuleRepository.save(rule);
    }

    public void updatePricingRule(PricingRule rule){
        pricingRuleRepository.update(rule);
    }
}
