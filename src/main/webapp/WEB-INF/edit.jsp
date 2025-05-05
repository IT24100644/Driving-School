<%@ page import="model.Instructor" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Instructor - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h4 class="text-center">Edit Instructor</h4>
                </div>
                <div class="card-body">
                    <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger">${param.error}</div>
                    <% } %>

                    <% Instructor instructor = (Instructor) request.getAttribute("instructor"); %>
                    <% List<String> certifications = (List<String>) request.getAttribute("certifications"); %>
                    <form action="${pageContext.request.contextPath}/instructor/update" method="post">
                        <input type="hidden" name="id" value="<%= instructor.getId() %>">

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Full Name</label>
                                <input type="text" class="form-control" name="name"
                                       value="<%= instructor.getName() %>" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Email</label>
                                <input type="email" class="form-control"
                                       value="<%= instructor.getEmail() %>" readonly>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Phone</label>
                                <input type="tel" class="form-control" name="phone"
                                       value="<%= instructor.getPhone() != null ? instructor.getPhone() : "" %>">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Address</label>
                                <input type="text" class="form-control" name="address"
                                       value="<%= instructor.getAddress() != null ? instructor.getAddress() : "" %>">
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Experience (Years)</label>
                                <input type="number" class="form-control" name="experienceYears"
                                       value="<%= instructor.getExperienceYears() %>" min="0" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Employment Type</label>
                                <div class="form-control" readonly>
                                    <%= instructor.isFullTime() ? "Full Time" : "Part Time" %>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Certifications</label>
                            <div class="row">
                                <% for (String cert : certifications) { %>
                                <div class="col-md-4">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="certifications"
                                               id="<%= cert.replace(" ", "") %>" value="<%= cert %>"
                                            <%= instructor.getCertifications().contains(cert) ? "checked" : "" %>>
                                        <label class="form-check-label" for="<%= cert.replace(" ", "") %>">
                                            <%= cert %>
                                        </label>
                                    </div>
                                </div>
                                <% } %>
                            </div>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Update Profile</button>
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