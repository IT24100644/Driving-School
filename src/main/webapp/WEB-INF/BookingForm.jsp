<%@ page import="model.Instructor" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Lesson - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h4 class="text-center">Book Driving Lesson</h4>
                </div>
                <div class="card-body">
                    <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger">${param.error}</div>
                    <% } %>

                    <form action="${pageContext.request.contextPath}/lesson/book" method="post">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Lesson Type</label>
                                <select class="form-select" name="lessonType" required>
                                    <option value="">Select Type</option>
                                    <option value="Beginner">Beginner Lesson (1 hour)</option>
                                    <option value="Advanced">Advanced Lesson (1.5 hours)</option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Car Type</label>
                                <select class="form-select" name="carType" required>
                                    <option value="">Select Car</option>
                                    <option value="Manual">Manual Transmission</option>
                                    <option value="Automatic">Automatic Transmission</option>
                                </select>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Instructor</label>
                            <select class="form-select" name="instructor" required>
                                <option value="">Select Instructor</option>
                                <% List<Instructor> instructors = (List<Instructor>) request.getAttribute("instructors"); %>
                                <% for (Instructor instructor : instructors) { %>
                                <option value="<%= instructor.getId() %>">
                                    <%= instructor.getName() %> (<%= instructor.isCertified() ? "Certified" : "Trainee" %>)
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Date & Time</label>
                            <input type="datetime-local" class="form-control" name="dateTime" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Location</label>
                            <input type="text" class="form-control" name="location" required>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Book Lesson</button>
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
        disable: [
            function(date) {
                return (date.getDay() === 0 || date.getDay() === 6); // Disable weekends
            }
        ]
    });
</script>
<%@ include file="/footer.jsp" %>
</body>
</html>