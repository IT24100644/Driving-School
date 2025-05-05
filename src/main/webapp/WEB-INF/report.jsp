<%@ page import="model.Progress" %>
<%@ page import="model.Student" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Progress Report - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .skill-item {
            margin-bottom: 10px;
        }
        .mastered {
            color: green;
        }
        .not-mastered {
            color: gray;
        }
    </style>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <% if (request.getParameter("success") != null) { %>
    <div class="alert alert-success">${param.success}</div>
    <% } %>

    <div class="card">
        <div class="card-header">
            <div class="d-flex justify-content-between align-items-center">
                <h4><i class="bi bi-file-text"></i> Progress Report</h4>
                <form action="${pageContext.request.contextPath}/progress/delete" method="post"
                      onsubmit="return confirm('Are you sure you want to delete this progress record?')">
                    <input type="hidden" name="studentId" value="<%= ((Progress)request.getAttribute("progress")).getStudentId() %>">
                    <button type="submit" class="btn btn-danger btn-sm">Delete Record</button>
                </form>
            </div>
        </div>
        <div class="card-body">
            <% Progress progress = (Progress) request.getAttribute("progress"); %>
            <% Student student = (Student) request.getAttribute("student"); %>

            <h5>Student: <%= student.getName() %></h5>
            <p>Email: <%= student.getEmail() %></p>

            <div class="row mt-4">
                <div class="col-md-6">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5>Skills Assessment</h5>
                        </div>
                        <div class="card-body">
                            <% if (progress.getSkills().isEmpty()) { %>
                            <p>No skills assessment available</p>
                            <% } else { %>
                            <ul class="list-unstyled">
                                <% for (Map.Entry<String, Boolean> entry : progress.getSkills().entrySet()) { %>
                                <li class="skill-item">
                                    <i class="bi <%= entry.getValue() ? "bi-check-circle-fill mastered" : "bi-dash-circle not-mastered" %>"></i>
                                    <%= entry.getKey() %>
                                </li>
                                <% } %>
                            </ul>
                            <% } %>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5>Progress Summary</h5>
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <strong>Overall Status:</strong>
                                <span class="badge bg-<%=
                                    progress.getOverallStatus().equals("Ready for Test") ? "success" :
                                    progress.getOverallStatus().equals("Intermediate") ? "warning" : "danger" %>">
                                    <%= progress.getOverallStatus() %>
                                </span>
                            </div>
                            <div class="mb-3">
                                <strong>Lessons Completed:</strong> <%= progress.getLessonsCompleted() %>
                            </div>
                            <div class="mb-3">
                                <strong>Lessons Attended:</strong> <%= progress.getLessonsAttended() %>
                            </div>
                            <div class="mb-3">
                                <strong>Skills Mastered:</strong>
                                <%= progress.getSkills().values().stream().filter(b -> b).count() %> /
                                <%= progress.getSkills().size() %>
                            </div>
                            <div class="progress" style="height: 20px;">
                                <div class="progress-bar" role="progressbar"
                                     style="width: <%= progress.getProgressPercentage() %>%"
                                     aria-valuenow="<%= progress.getProgressPercentage() %>"
                                     aria-valuemin="0" aria-valuemax="100">
                                    <%= String.format("%.1f", progress.getProgressPercentage()) %>%
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h5>Lesson Feedback</h5>
                </div>
                <div class="card-body">
                    <% if (progress.getLessonFeedback().isEmpty()) { %>
                    <p>No feedback available yet</p>
                    <% } else { %>
                    <div class="list-group">
                        <% for (Map.Entry<String, String> entry : progress.getLessonFeedback().entrySet()) { %>
                        <div class="list-group-item">
                            <div class="d-flex w-100 justify-content-between">
                                <h6 class="mb-1">Lesson <%= entry.getKey() %></h6>
                            </div>
                            <p class="mb-1"><%= entry.getValue() %></p>
                        </div>
                        <% } %>
                    </div>
                    <% } %>
                </div>
            </div>

            <div class="mt-4">
                <a href="${pageContext.request.contextPath}/progress/update?studentId=<%= progress.getStudentId() %>"
                   class="btn btn-warning">Update Progress</a>
                <a href="${pageContext.request.contextPath}/progress/dashboard"
                   class="btn btn-secondary">Back to Dashboard</a>
            </div>
        </div>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>