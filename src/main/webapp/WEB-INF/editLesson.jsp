<%@ page import="model.Lesson" %>
<%@ page import="model.Instructor" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reschedule Lesson - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../fragments/header.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h4 class="text-center">Reschedule Lesson</h4>
                </div>
                <div class="card-body">
                    <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger">${param.error}</div>
                    <% } %>

                    <% Lesson lesson = (Lesson) request.getAttribute("lesson"); %>
                    <form action="${pageContext.request.contextPath}/lesson/update" method="post">
                        <input type="hidden" name="id" value="<%= lesson.getId() %>">

                        <div class="mb-3">
                            <label class="form-label">Current Date & Time</label>
                            <input type="text" class="form-control"
                                   value="<%= lesson.getStartTime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")) %>"
                                   readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">New Date & Time</label>
                            <input type="datetime-local" class="form-control" name="dateTime" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Instructor</label>
                            <select class="form-select" name="instructor" required>
                                <% List<Instructor> instructors = (List<Instructor>) request.getAttribute("instructors"); %>
                                <% for (Instructor instructor : instructors) { %>
                                <option value="<%= instructor.getId() %>"
                                        <%= instructor.getId().equals(lesson.getInstructor().getId()) ? "selected" : "" %>>
                                    <%= instructor.getName() %>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Status</label>
                            <select class="form-select" name="status">
                                <option value="Scheduled" <%= lesson.getStatus().equals("Scheduled") ? "selected" : "" %>>Scheduled</option>
                                <option value="Completed" <%= lesson.getStatus().equals("Completed") ? "selected" : "" %>>Completed</option>
                                <option value="Cancelled" <%= lesson.getStatus().equals("Cancelled") ? "selected" : "" %>>Cancelled</option>
                            </select>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Update Lesson</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script>
    flatpickr("input[type=datetime-local]", {
        enableTime: true,
        dateFormat: "Y-m-d\\TH:i",
        minTime: "08:00",
        maxTime: "20:00",
        defaultDate: "<%= lesson.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) %>",
        disable: [
            function(date) {
                return (date.getDay() === 0 || date.getDay() === 6); // Disable weekends
            }
        ]
    });
</script>
<%@ include file="../fragments/footer.jsp" %>
</body>
</html>