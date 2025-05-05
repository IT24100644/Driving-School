<%@ page import="model.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lesson Calendar - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Lesson Calendar</h2>
        <form method="get" action="${pageContext.request.contextPath}/lesson/calendar" class="row g-3">
            <div class="col-auto">
                <input type="date" name="date" value="${param.date}" class="form-control">
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary">Go</button>
            </div>
        </form>
    </div>

    <div class="card">
        <div class="card-body">
            <div id="calendar"></div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const calendarEl = document.getElementById('calendar');
        const calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay'
            },
            events: [
                <% List<Lesson> lessons = (List<Lesson>) request.getAttribute("lessons"); %>
                <% for (Lesson lesson : lessons) { %>
                {
                    title: '<%= lesson.getStudent().getName() %> - <%= lesson.getLessonType() %>',
                    start: '<%= lesson.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) %>',
                    end: '<%= lesson.getStartTime().plusMinutes(lesson.getDuration())
                            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) %>',
                    color: '<%= lesson.getStatus().equals("Scheduled") ? "#0d6efd" :
                             lesson.getStatus().equals("Completed") ? "#198754" : "#dc3545" %>'
                },
                <% } %>
            ]
        });
        calendar.render();
    });
</script>
<%@ include file="/footer.jsp" %>
</body>
</html>