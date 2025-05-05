<%@ page import="model.Instructor" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Instructors - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Instructor Management</h2>
        <div>
            <a href="${pageContext.request.contextPath}/instructor/register" class="btn btn-success me-2">Add New</a>
            <form class="d-inline" method="get" action="${pageContext.request.contextPath}/instructor/list">
                <div class="input-group">
                    <input type="text" class="form-control" name="query" placeholder="Search...">
                    <button class="btn btn-outline-secondary" type="submit">Search</button>
                </div>
            </form>
        </div>
    </div>

    <% if (request.getParameter("success") != null) { %>
    <div class="alert alert-success">${param.success}</div>
    <% } %>
    <% if (request.getParameter("error") != null) { %>
    <div class="alert alert-danger">${param.error}</div>
    <% } %>

    <div class="card">
        <div class="card-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Type</th>
                    <th>Experience</th>
                    <th>Certifications</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% List<Instructor> instructors = (List<Instructor>) request.getAttribute("instructors"); %>
                <% for (Instructor instructor : instructors) { %>
                <tr>
                    <td><%= instructor.getId() %></td>
                    <td><%= instructor.getName() %></td>
                    <td><%= instructor.getEmail() %></td>
                    <td><%= instructor.isFullTime() ? "Full Time" : "Part Time" %></td>
                    <td><%= instructor.getExperienceYears() %> years</td>
                    <td>
                        <% if (instructor.getCertifications().isEmpty()) { %>
                        None
                        <% } else { %>
                        <%= String.join(", ", instructor.getCertifications()) %>
                        <% } %>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/instructor/profile?id=<%= instructor.getId() %>"
                           class="btn btn-sm btn-info">View</a>
                        <a href="${pageContext.request.contextPath}/instructor/edit?id=<%= instructor.getId() %>"
                           class="btn btn-sm btn-warning">Edit</a>
                        <form action="${pageContext.request.contextPath}/instructor/delete" method="post"
                              style="display: inline;">
                            <input type="hidden" name="id" value="<%= instructor.getId() %>">
                            <button type="submit" class="btn btn-sm btn-danger"
                                    onclick="return confirm('Are you sure?')">Delete</button>
                        </form>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>