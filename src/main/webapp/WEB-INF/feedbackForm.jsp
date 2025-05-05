<%@ page import="model.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Submit Feedback - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="card">
        <div class="card-header">
            <h4><i class="bi bi-chat-left-text"></i> Lesson Feedback</h4>
        </div>
        <div class="card-body">
            <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger">${param.error}</div>
            <% } %>

            <% Student student = (Student) request.getAttribute("student"); %>
            <form action="${pageContext.request.contextPath}/progress/feedback" method="post">
                <input type="hidden" name="studentId" value="<%= student.getId() %>">
                <input type="hidden" name="lessonId" value="<%= request.getParameter("lessonId") %>">

                <div class="mb-3">
                    <label class="form-label">Student</label>
                    <input type="text" class="form-control" value="<%= student.getName() %>" readonly>
                </div>

                <div class="mb-3">
                    <label class="form-label">Lesson ID</label>
                    <input type="text" class="form-control" value="<%= request.getParameter("lessonId") %>" readonly>
                </div>

                <div class="mb-3">
                    <label for="feedback" class="form-label">Feedback</label>
                    <textarea class="form-control" id="feedback" name="feedback" rows="5" required></textarea>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-primary">Submit Feedback</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>
