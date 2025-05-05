<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Make Payment - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .payment-method {
            display: none;
        }
        .active-method {
            display: block;
        }
    </style>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h4 class="text-center">Make Payment</h4>
                </div>
                <div class="card-body">
                    <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger">${param.error}</div>
                    <% } %>

                    <form action="${pageContext.request.contextPath}/payment/process" method="post">
                        <div class="mb-3">
                            <label class="form-label">Amount Due</label>
                            <input type="text" class="form-control" value="${dueAmount}" readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Amount to Pay</label>
                            <input type="number" class="form-control" name="amount"
                                   min="1" step="0.01" value="${dueAmount}" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <input type="text" class="form-control" name="description"
                                   value="Driving Lessons Fee" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Payment Method</label>
                            <select class="form-select" id="paymentMethod" name="paymentMethod" required>
                                <option value="">Select Payment Method</option>
                                <option value="card">Credit/Debit Card</option>
                                <option value="bank">Bank Transfer</option>
                                <option value="cash">Cash</option>
                            </select>
                        </div>

                        <!-- Card Payment Fields -->
                        <div id="cardFields" class="payment-method">
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Card Number</label>
                                    <input type="text" class="form-control" name="cardNumber"
                                           placeholder="1234 5678 9012 3456">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Card Holder Name</label>
                                    <input type="text" class="form-control" name="cardHolder"
                                           placeholder="John Doe">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-4">
                                    <label class="form-label">Expiry Date</label>
                                    <input type="text" class="form-control" name="expiryDate"
                                           placeholder="MM/YY">
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">CVV</label>
                                    <input type="text" class="form-control" name="cvv"
                                           placeholder="123">
                                </div>
                            </div>
                        </div>

                        <!-- Bank Transfer Fields -->
                        <div id="bankFields" class="payment-method">
                            <div class="mb-3">
                                <label class="form-label">Bank Name</label>
                                <input type="text" class="form-control" name="bankName">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Account Number</label>
                                <input type="text" class="form-control" name="accountNumber">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Reference</label>
                                <input type="text" class="form-control" name="reference">
                            </div>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Submit Payment</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.getElementById('paymentMethod').addEventListener('change', function() {
        // Hide all payment method fields
        document.querySelectorAll('.payment-method').forEach(el => {
            el.classList.remove('active-method');
        });

        // Show selected payment method fields
        const method = this.value;
        if (method === 'card') {
            document.getElementById('cardFields').classList.add('active-method');
        } else if (method === 'bank') {
            document.getElementById('bankFields').classList.add('active-method');
        }
        // Cash payment has no additional fields
    });
</script>
<%@ include file="/footer.jsp" %>
</body>
</html>