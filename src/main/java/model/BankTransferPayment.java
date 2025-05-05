package model;

public class BankTransferPayment extends Payment {
    private String accountNumber;
    private String bankName;
    private String reference;

    public BankTransferPayment() {
        super();
    }

    public BankTransferPayment(String paymentId, String studentId, double amount, String description) {
        super(paymentId, studentId, amount, description);
    }

    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
    }

    @Override
    public String getPaymentDetails() {
        return bankName + " (Ref: " + reference + ")";
    }
}
