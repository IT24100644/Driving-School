<%@ page import="model.Progress" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Progress - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .progress-bar {
            transition: width 1s ease-in-out;
        }
        .skill-badge {
            font-size: 0.9rem;
            margin-right: 5px;
            margin-bottom: 5px;
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
            <h4><i class="bi bi-speedometer2"></i> My Progress Dashboard</h4>
        </div>
        <div class="card-body">
            <% Progress progress = (Progress) request.getAttribute("progress"); %>

            <div class="row mb-4">
                <div class="col-md-6">
                    <h5>Overall Progress</h5>
                    <div class="progress mb-3" style="height: 30px;">
                        <div class="progress-bar bg-success" role="progressbar"
                             style="width: <%= progress.getProgressPercentage() %>%"
                             aria-valuenow="<%= progress.getProgressPercentage() %>"
                             aria-valuemin="0" aria-valuemax="100">
                            <%= String.format("%.1f", progress.getProgressPercentage()) %>%
                        </div>
                    </div>
                    <p><strong>Status:</strong>
                        <span class="badge bg-<%=
                            progress.getOverallStatus().equals("Ready for Test") ? "success" :
                            progress.getOverallStatus().equals("Intermediate") ? "warning" : "danger" %>">
                            <%= progress.getOverallStatus() %>
                        </span>
                    </p>
                    <p><strong>Lessons Completed:</strong> <%= progress.getLessonsCompleted() %></p>
                    <p><strong>Lessons Attended:</strong> <%= progress.getLessonsAttended() %></p>
                </div>
                <div class="col-md-6">
                    <h5>Skills Mastered</h5>
                    <% if (progress.getSkills().isEmpty()) { %>
                    <p>No skills recorded yet</p>
                    <% } else { %>
                    <% for (Map.Entry<String, Boolean> entry : progress.getSkills().entrySet()) { %>
                    <span class="badge <%= entry.getValue() ? "bg-success" : "bg-secondary" %> skill-badge">
                                <%= entry.getKey() %>
                                <i class="bi <%= entry.getValue() ? "bi-check-circle-fill" : "bi-dash-circle" %>"></i>
                            </span>
                    <% } %>
                    <% } %>
                </div>
            </div>

            <div class="row">
                <div class="col-12">
                    <h5>Recent Feedback</h5>
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
        </div>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>