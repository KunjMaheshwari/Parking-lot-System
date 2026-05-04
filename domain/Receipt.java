package domain;

import java.time.LocalDateTime;
import java.util.*;

public class Receipt {
    private UUID id;
    private UUID ticketId;
    private LocalDateTime exitTime;
    private double totalFare;
    private PaymentStatus paymentStatus;

    public enum PaymentStatus{
        PENDING, SUCCESS, FAILED
    }

    public Receipt(UUID ticketId, double totalFare){
        this.id = UUID.randomUUID();
        this.ticketId =ticketId;
        this.exitTime = LocalDateTime.now();
        this.totalFare = totalFare;
        this.paymentStatus = PaymentStatus.PENDING;
    }   

    public void markAsPaid(){
        this.paymentStatus = PaymentStatus.SUCCESS;
    }

    public void markAsFail(){
        this.paymentStatus = PaymentStatus.FAILED;
    }

    //Getter and Setter

    public UUID getId(){
        return id;
    }

    public UUID getTicketId(){
        return ticketId;
    }

    public LocalDateTime getExitTime(){
        return exitTime;
    }

    public double getTotalFare(){
        return totalFare;
    }

    public PaymentStatus getPaymentStatus(){
        return paymentStatus;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", exitTime=" + exitTime +
                ", totalFee=" + totalFare +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
