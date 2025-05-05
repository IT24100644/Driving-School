<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Book a Driving Lesson</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">Book a Driving Lesson</h2>
    <form action="ScheduleLessonServlet" method="post" class="card p-4 shadow-sm">
        <div class="mb-3">
            <label for="studentName" class="form-label">Your Name</label>
            <input type="text" name="studentName" class="form-control" required />
        </div>
        <div class="mb-3">
            <label for="instructorName" class="form-label">Instructor Name</label>
            <input type="text" name="instructorName" class="form-control" required />
            <!-- Optional: Replace this with a dropdown populated from instructors.txt -->
        </div>
        <div class="mb-3">
            <label for="date" class="form-label">Lesson Date</label>
            <input type="date" name="date" class="form-control" required />
        </div>
        <div class="mb-3">
            <label for="time" class="form-label">Lesson Time</label>
            <input type="time" name="time" class="form-control" required />
        </div>
        <button type="submit" class="btn btn-primary">Schedule Lesson</button>
    </form>
</div>
</body>
</html>
