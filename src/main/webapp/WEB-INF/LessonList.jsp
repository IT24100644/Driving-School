<%@ page import="model.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Lessons - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../fragments/header.jsp" %>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>My Scheduled Lessons</h2>
        <a href="${pageContext.request.contextPath}/lesson/book" class="btn btn-success">Book New Lesson</a>
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
                    <th>Date & Time</th>
                    <th>Duration</th>
                    <th>Type</th>
                    <th>Instructor</th>
                    <th>Car</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% List<Lesson> lessons = (List<Lesson>) request.getAttribute("lessons"); %>
                <% for (Lesson lesson : lessons) { %>
                <tr>
                    <td><%= lesson.getId() %></td>
                    <td><%= lesson.getStartTime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")) %></td>
                    <td><%= lesson.getDuration() %> minutes</td>
                    <td><%= lesson.getLessonType() %></td>
                    <td><%= lesson.getInstructor().getName() %></td>
                    <td><%= lesson.getCarType() %></td>
                    <td><span class="badge bg-<%= lesson.getStatus().equals("Scheduled") ? "primary" :
                                              lesson.getStatus().equals("Completed") ? "success" : "danger" %>">
                            <%= lesson.getStatus() %>
                        </span></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/lesson/edit?id=<%= lesson.getId() %>"
                           class="btn btn-sm btn-warning">Reschedule</a>
                        <% if (lesson.getStatus().equals("Scheduled")) { %>
                        <form action="${pageContext.request.contextPath}/lesson/cancel" method="post"
                              style="display: inline;">
                            <input type="hidden" name="id" value="<%= lesson.getId() %>">
                            <button type="submit" class="btn btn-sm btn-danger"
                                    onclick="return confirm('Are you sure you want to cancel this lesson?')">Cancel</button>
                        </form>
                        <% } %>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="../fragments/footer.jsp" %>
</body>
</html>