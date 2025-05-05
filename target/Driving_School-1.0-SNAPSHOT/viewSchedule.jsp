<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Scheduled Lessons</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">Scheduled Driving Lessons</h2>
    <table class="table table-bordered table-striped shadow-sm">
        <thead class="table-dark">
        <tr>
            <th>Student Name</th>
            <th>Instructor</th>
            <th>Date</th>
            <th>Time</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="lesson" items="${lessonQueue}">
            <tr>
                <td>${lesson.studentName}</td>
                <td>${lesson.instructorName}</td>
                <td>${lesson.date}</td>
                <td>${lesson.time}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
