package model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Payment implements Serializable {
    private String paymentId;
    private String studentId;
    private double amount;
    private LocalDate paymentDate;
    private String status; // Pending, Completed, Failed, Refunded
    private String invoiceNumber;
    private String description;

    public Payment() {
        this.paymentDate = LocalDate.now();
        this.status = "Pending";
    }

    public Payment(String paymentId, String studentId, double amount, String description) {
        this();
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.amount = amount;
        this.description = description;
        this.invoiceNumber = generateInvoiceNumber();
    }

    // Getters and Setters
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Abstract methods
    public abstract String getPaymentMethod();
    public abstract String getPaymentDetails();

    // Generate invoice number
    private String generateInvoiceNumber() {
        return "INV-" + LocalDate.now().getYear() + "-" + System.currentTimeMillis();
    }

    // Format amount for display
    public String getFormattedAmount() {
        return String.format("$%.2f", amount);
    }
}

