<%@ page import="model.Payment" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Payment History - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <% if (request.getParameter("success") != null) { %>
    <div class="alert alert-success">${param.success}</div>
    <% } %>

    <div class="card">
        <div class="card-header">
            <h4><i class="bi bi-clock-history"></i> Payment History</h4>
        </div>
        <div class="card-body">
            <div class="row mb-4">
                <div class="col-md-6">
                    <div class="card bg-light">
                        <div class="card-body">
                            <h5>Total Paid: $<%= String.format("%.2f", request.getAttribute("totalPaid")) %></h5>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card bg-light">
                        <div class="card-body">
                            <h5>Amount Due: $<%= String.format("%.2f", request.getAttribute("totalDue")) %></h5>
                        </div>
                    </div>
                </div>
            </div>

            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Invoice #</th>
                    <th>Amount</th>
                    <th>Method</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% List<Payment> payments = (List<Payment>) request.getAttribute("payments"); %>
                <% for (Payment payment : payments) { %>
                <tr>
                    <td><%= payment.getPaymentDate() %></td>
                    <td><%= payment.getInvoiceNumber() %></td>
                    <td>$<%= String.format("%.2f", payment.getAmount()) %></td>
                    <td><%= payment.getPaymentMethod() %></td>
                    <td>
                            <span class="badge bg-<%=
                                payment.getStatus().equals("Completed") ? "success" :
                                payment.getStatus().equals("Pending") ? "warning" : "danger" %>">
                                <%= payment.getStatus() %>
                            </span>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/payment/invoice?id=<%= payment.getPaymentId() %>"
                           class="btn btn-sm btn-info">Invoice</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>

            <div class="mt-3">
                <a href="${pageContext.request.contextPath}/payment/process" class="btn btn-primary">Make New Payment</a>
            </div>
        </div>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>