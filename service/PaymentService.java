package service;

import domain.*;
import domain.Payment.PaymentGateway;

import java.util.*;

public class PaymentService {
    private PaymentRepository paymentRepository;
    private PaymentGatewayAdapter defaultGateway;

    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
        this.defaultGateway = new RazorpayAdapter();
    }

    public boolean processPayment(UUID ticketId, double amount){
        Payment payment = new Payment(ticketId, amount, PaymentGateway.RAZORPAY);
        paymentRepository.save(payment);

        boolean success = defaultGateway.pay(ticketId, amount);

        if(success){
            payment.markAsSuccess();
        }else{
            payment.markAsFailed();
        }
        paymentRepository.update(payment);

        return success;
    }

    public boolean processPaymentWithRetry(UUID ticketId, double amount, int maxRetries){
        for(int attempt = 1; attempt <= maxRetries; attempt++){
            boolean success = processPayment(ticketId, amount);

            if(success){
                return true;
            }

            if(attempt > 1){
                defaultGateway = new StripeAdapter();
                System.out.println("Switching to Stripe payment gateway for retry");
            }
        }
        System.out.println("Payment failed: " + maxRetries + " attempts");
        return false;
    }

    public void setDefaultGateway(PaymentGatewayAdapter gateway){
        this.defaultGateway = gateway;
    }
}
