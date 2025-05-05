<%@ page import="model.Payment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Payment - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h4 class="text-center">Update Payment Status</h4>
                </div>
                <div class="card-body">
                    <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger">${param.error}</div>
                    <% } %>

                    <% Payment payment = (Payment) request.getAttribute("payment"); %>
                    <form action="${pageContext.request.contextPath}/payment/update" method="post">
                        <input type="hidden" name="id" value="<%= payment.getPaymentId() %>">

                        <div class="mb-3">
                            <label class="form-label">Invoice Number</label>
                            <input type="text" class="form-control" value="<%= payment.getInvoiceNumber() %>" readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Student ID</label>
                            <input type="text" class="form-control" value="<%= payment.getStudentId() %>" readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Amount</label>
                            <input type="text" class="form-control" value="$<%= String.format("%.2f", payment.getAmount()) %>" readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Payment Method</label>
                            <input type="text" class="form-control" value="<%= payment.getPaymentMethod() %>" readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Status</label>
                            <select class="form-select" name="status" required>
                                <option value="Pending" <%= payment.getStatus().equals("Pending") ? "selected" : "" %>>Pending</option>
                                <option value="Completed" <%= payment.getStatus().equals("Completed") ? "selected" : "" %>>Completed</option>
                                <option value="Failed" <%= payment.getStatus().equals("Failed") ? "selected" : "" %>>Failed</option>
                                <option value="Refunded" <%= payment.getStatus().equals("Refunded") ? "selected" : "" %>>Refunded</option>
                            </select>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Update Status</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>