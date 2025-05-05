package model;

public class CashPayment extends Payment {

    public CashPayment() {
        super();
    }

    public CashPayment(String paymentId, String studentId, double amount, String description) {
        super(paymentId, studentId, amount, description);
    }

    @Override
    public String getPaymentMethod() {
        return "Cash";
    }

    @Override
    public String getPaymentDetails() {
        return "Paid in person";
    }
}
