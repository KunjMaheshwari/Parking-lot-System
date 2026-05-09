package service;

import domain.*;

public class ReceiptService {
    public ReceiptService(){
        System.out.println("ReceiptService initiated");
    }

    public Receipt generateReceipt(Ticket ticket, double fee){
        Receipt receipt = new Receipt(ticket.getId(), fee);
        return receipt;
    }

    public void markReceiptAsPaid(Receipt receipt){
        receipt.markAsPaid();
    }

    public String generateReceiptText(Receipt receipt, Ticket ticket){
        StringBuilder receiptText = new StringBuilder();

        receiptText.append(receipt.getId());
        receiptText.append(ticket.getId());
        receiptText.append(ticket.getEntryTime());
        receiptText.append(receipt.getExitTime());
        receiptText.append(receipt.getTotalFare());
        receiptText.append(receipt.getPaymentStatus());

        return receiptText.toString();
    }
}
