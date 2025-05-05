<%@ page import="model.User" %>
<%@ page import="model.Instructor" %>
<%@ page import="model.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Profile - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../fragments/header.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h4 class="text-center">Update Profile</h4>
                </div>
                <div class="card-body">
                    <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger">${param.error}</div>
                    <% } %>

                    <% User user = (User) request.getAttribute("user"); %>
                    <form action="${pageContext.request.contextPath}/user/update" method="post">
                        <input type="hidden" name="id" value="<%= user.getId() %>">

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Full Name</label>
                                <input type="text" class="form-control" name="name"
                                       value="<%= user.getName() %>" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Email</label>
                                <input type="email" class="form-control"
                                       value="<%= user.getEmail() %>" readonly>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Phone</label>
                                <input type="tel" class="form-control" name="phone"
                                       value="<%= user.getPhone() != null ? user.getPhone() : "" %>">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Address</label>
                                <input type="text" class="form-control" name="address"
                                       value="<%= user.getAddress() != null ? user.getAddress() : "" %>">
                            </div>
                        </div>

                        <% if (user instanceof Instructor) { %>
                        <div class="mb-3">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="certified"
                                    <%= ((Instructor)user).isCertified() ? "checked" : "" %>>
                                <label class="form-check-label">Certified Instructor</label>
                            </div>
                        </div>
                        <% } else if (user instanceof Student) { %>
                        <div class="mb-3">
                            <label class="form-label">License Number</label>
                            <input type="text" class="form-control" name="licenseNumber"
                                   value="<%= ((Student)user).getLicenseNumber() != null ?
                                   ((Student)user).getLicenseNumber() : "" %>">
                        </div>
                        <% } %>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Update Profile</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../fragments/footer.jsp" %>
</body>
</html>