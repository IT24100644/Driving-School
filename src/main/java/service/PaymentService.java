package service;

import model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import util.FileHandler;

public class PaymentService {
    private static final String PAYMENTS_FILE = "payments.txt";
    private UserService userService;

    public PaymentService(UserService userService) {
        this.userService = userService;
    }

    public boolean processPayment(Payment payment) {
        List<Payment> payments = getAllPayments();
        payments.add(payment);
        return FileHandler.savePayments(payments, PAYMENTS_FILE);
    }

    public List<Payment> getAllPayments() {
        return FileHandler.loadPayments(PAYMENTS_FILE);
    }

    public List<Payment> getPaymentsByStudent(String studentId) {
        return getAllPayments().stream()
                .filter(p -> p.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Payment> getPendingPayments() {
        return getAllPayments().stream()
                .filter(p -> p.getStatus().equals("Pending"))
                .collect(Collectors.toList());
    }

    public Payment getPaymentById(String paymentId) {
        return getAllPayments().stream()
                .filter(p -> p.getPaymentId().equals(paymentId))
                .findFirst()
                .orElse(null);
    }

    public boolean updatePaymentStatus(String paymentId, String status) {
        List<Payment> payments = getAllPayments();
        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(paymentId)) {
                payment.setStatus(status);
                return FileHandler.savePayments(payments, PAYMENTS_FILE);
            }
        }
        return false;
    }

    public boolean deletePayment(String paymentId) {
        List<Payment> payments = getAllPayments();
        boolean removed = payments.removeIf(p -> p.getPaymentId().equals(paymentId));
        if (removed) {
            return FileHandler.savePayments(payments, PAYMENTS_FILE);
        }
        return false;
    }

    public double getTotalPaidByStudent(String studentId) {
        return getPaymentsByStudent(studentId).stream()
                .filter(p -> p.getStatus().equals("Completed"))
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    public double getTotalDueByStudent(String studentId) {
        // In a real system, this would calculate based on lessons taken minus payments made
        // For simplicity, we'll assume each student has a fixed course fee of $1000
        final double COURSE_FEE = 1000.00;
        return COURSE_FEE - getTotalPaidByStudent(studentId);
    }

    public String generateInvoice(Payment payment) {
        Student student = (Student) userService.getUserById(payment.getStudentId());

        StringBuilder invoice = new StringBuilder();
        invoice.append("Invoice Number: ").append(payment.getInvoiceNumber()).append("\n");
        invoice.append("Date: ").append(LocalDate.now()).append("\n\n");
        invoice.append("Bill To:\n");
        invoice.append(student.getName()).append("\n");
        invoice.append(student.getEmail()).append("\n\n");
        invoice.append("Description: ").append(payment.getDescription()).append("\n");
        invoice.append("Amount: ").append(payment.getFormattedAmount()).append("\n");
        invoice.append("Payment Method: ").append(payment.getPaymentMethod()).append("\n");
        invoice.append("Status: ").append(payment.getStatus()).append("\n\n");
        invoice.append("Thank you for your business!");

        return invoice.toString();
    }

    public String generateCorporateInvoice(Payment payment) {
        Student student = (Student) userService.getUserById(payment.getStudentId());

        StringBuilder invoice = new StringBuilder();
        invoice.append("TAX INVOICE\n");
        invoice.append("Invoice Number: ").append(payment.getInvoiceNumber()).append("\n");
        invoice.append("Date: ").append(LocalDate.now()).append("\n\n");
        invoice.append("From:\nDriving School\n123 Main St\nCity, State ZIP\n\n");
        invoice.append("To:\n").append(student.getName()).append("\n");
        invoice.append(student.getEmail()).append("\n\n");
        invoice.append("Description\t\tAmount\n");
        invoice.append("----------------\t--------\n");
        invoice.append(payment.getDescription()).append("\t\t").append(payment.getFormattedAmount()).append("\n");
        invoice.append("----------------\t--------\n");
        invoice.append("Total\t\t\t").append(payment.getFormattedAmount()).append("\n\n");
        invoice.append("Payment Method: ").append(payment.getPaymentMethod()).append("\n");
        invoice.append("Payment Status: ").append(payment.getStatus()).append("\n");
        invoice.append("Terms: Due upon receipt\n\n");
        invoice.append("Thank you for your business!");

        return invoice.toString();
    }
}
