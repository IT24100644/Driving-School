package model;

public class CardPayment extends Payment {
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvv;

    public CardPayment() {
        super();
    }

    public CardPayment(String paymentId, String studentId, double amount, String description) {
        super(paymentId, studentId, amount, description);
    }

    // Getters and Setters
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCardHolder() { return cardHolder; }
    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    @Override
    public String getPaymentMethod() {
        return "Credit/Debit Card";
    }

    @Override
    public String getPaymentDetails() {
        return "Card ending with " + cardNumber.substring(cardNumber.length() - 4);
    }
}
