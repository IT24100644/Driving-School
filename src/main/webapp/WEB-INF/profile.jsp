<%@ page import="model.Instructor" %>
<%@ page import="model.AvailabilitySlot" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Instructor Profile - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <% if (request.getParameter("success") != null) { %>
    <div class="alert alert-success">${param.success}</div>
    <% } %>

    <% Instructor instructor = (Instructor) request.getAttribute("instructor"); %>
    <div class="card">
        <div class="card-header">
            <div class="d-flex justify-content-between align-items-center">
                <h4><%= instructor.getName() %>'s Profile</h4>
                <a href="${pageContext.request.contextPath}/instructor/availability?id=<%= instructor.getId() %>"
                   class="btn btn-outline-primary">Update Availability</a>
            </div>
        </div>
        <div class="card-body">
            <div class="row mb-4">
                <div class="col-md-6">
                    <h5>Basic Information</h5>
                    <p><strong>ID:</strong> <%= instructor.getId() %></p>
                    <p><strong>Email:</strong> <%= instructor.getEmail() %></p>
                    <p><strong>Phone:</strong> <%= instructor.getPhone() != null ? instructor.getPhone() : "N/A" %></p>
                    <p><strong>Address:</strong> <%= instructor.getAddress() != null ? instructor.getAddress() : "N/A" %></p>
                </div>
                <div class="col-md-6">
                    <h5>Professional Details</h5>
                    <p><strong>Type:</strong> <%= instructor.isFullTime() ? "Full Time" : "Part Time" %></p>
                    <p><strong>Experience:</strong> <%= instructor.getExperienceYears() %> years</p>
                    <p><strong>Gender:</strong> <%= instructor.isFemale() ? "Female" : "Male" %></p>
                    <p><strong>Certifications:</strong></p>
                    <ul>
                        <% if (instructor.getCertifications().isEmpty()) { %>
                        <li>None</li>
                        <% } else { %>
                        <% for (String cert : instructor.getCertifications()) { %>
                        <li><%= cert %></li>
                        <% } %>
                        <% } %>
                    </ul>
                </div>
            </div>

            <div class="row">
                <div class="col-12">
                    <h5>Availability</h5>
                    <% if (instructor.getAvailability().isEmpty()) { %>
                    <p>No availability set</p>
                    <% } else { %>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>Day</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (AvailabilitySlot slot : instructor.getAvailability()) { %>
                        <tr>
                            <td><%= slot.getDayOfWeek() %></td>
                            <td><%= slot.getStartTime() %></td>
                            <td><%= slot.getEndTime() %></td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                    <% } %>
                </div>
            </div>

            <div class="mt-4">
                <a href="${pageContext.request.contextPath}/instructor/edit?id=<%= instructor.getId() %>"
                   class="btn btn-warning">Edit Profile</a>
                <a href="${pageContext.request.contextPath}/instructor/list"
                   class="btn btn-secondary">Back to List</a>
            </div>
        </div>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>