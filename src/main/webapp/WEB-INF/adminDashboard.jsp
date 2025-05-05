<%@ page import="model.Payment" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Billing Dashboard - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <% if (request.getParameter("success") != null) { %>
    <div class="alert alert-success">${param.success}</div>
    <% } %>
    <% if (request.getParameter("error") != null) { %>
    <div class="alert alert-danger">${param.error}</div>
    <% } %>

    <div class="card">
        <div class="card-header">
            <div class="d-flex justify-content-between align-items-center">
                <h4><i class="bi bi-cash-stack"></i> Billing Dashboard</h4>
                <div class="badge bg-primary fs-6">
                    Total Revenue: $<%= String.format("%.2f", request.getAttribute("totalRevenue")) %>
                </div>
            </div>
        </div>
        <div class="card-body">
            <h5 class="mb-4">Pending Payments</h5>
            <% List<Payment> pendingPayments = (List<Payment>) request.getAttribute("pendingPayments"); %>
            <% if (pendingPayments.isEmpty()) { %>
            <p>No pending payments</p>
            <% } else { %>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Student ID</th>
                    <th>Invoice #</th>
                    <th>Amount</th>
                    <th>Method</th>
                    <th>Date</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Payment payment : pendingPayments) { %>
                <tr>
                    <td><%= payment.getStudentId() %></td>
                    <td><%= payment.getInvoiceNumber() %></td>
                    <td>$<%= String.format("%.2f", payment.getAmount()) %></td>
                    <td><%= payment.getPaymentMethod() %></td>
                    <td><%= payment.getPaymentDate() %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/payment/edit?id=<%= payment.getPaymentId() %>"
                           class="btn btn-sm btn-warning">Update</a>
                        <a href="${pageContext.request.contextPath}/payment/invoice?id=<%= payment.getPaymentId() %>"
                           class="btn btn-sm btn-info">Invoice</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } %>

            <h5 class="mt-5 mb-4">All Payments</h5>
            <% List<Payment> allPayments = (List<Payment>) request.getAttribute("allPayments"); %>
            <% if (allPayments.isEmpty()) { %>
            <p>No payment records</p>
            <% } else { %>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Student ID</th>
                    <th>Invoice #</th>
                    <th>Amount</th>
                    <th>Method</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Payment payment : allPayments) { %>
                <tr>
                    <td><%= payment.getStudentId() %></td>
                    <td><%= payment.getInvoiceNumber() %></td>
                    <td>$<%= String.format("%.2f", payment.getAmount()) %></td>
                    <td><%= payment.getPaymentMethod() %></td>
                    <td><%= payment.getPaymentDate() %></td>
                    <td>
                                <span class="badge bg-<%=
                                    payment.getStatus().equals("Completed") ? "success" :
                                    payment.getStatus().equals("Pending") ? "warning" : "danger" %>">
                                    <%= payment.getStatus() %>
                                </span>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/payment/edit?id=<%= payment.getPaymentId() %>"
                           class="btn btn-sm btn-warning">Update</a>
                        <a href="${pageContext.request.contextPath}/payment/invoice?id=<%= payment.getPaymentId() %>"
                           class="btn btn-sm btn-info">Invoice</a>
                        <form action="${pageContext.request.contextPath}/payment/delete" method="post"
                              style="display: inline;">
                            <input type="hidden" name="id" value="<%= payment.getPaymentId() %>">
                            <button type="submit" class="btn btn-sm btn-danger"
                                    onclick="return confirm('Are you sure?')">Delete</button>
                        </form>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } %>
        </div>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>
