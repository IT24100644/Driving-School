<%@ page import="model.Progress" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Progress - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="card">
        <div class="card-header">
            <h4><i class="bi bi-people-fill"></i> Student Progress Overview</h4>
        </div>
        <div class="card-body">
            <% if (request.getParameter("success") != null) { %>
            <div class="alert alert-success">${param.success}</div>
            <% } %>
            <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger">${param.error}</div>
            <% } %>

            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Student</th>
                    <th>Status</th>
                    <th>Completed</th>
                    <th>Mastered Skills</th>
                    <th>Progress</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% List<Progress> progressList = (List<Progress>) request.getAttribute("progressList"); %>
                <% for (Progress progress : progressList) { %>
                <tr>
                    <td><%= ((Student)request.getAttribute("student")).getName() %></td>
                    <td>
                            <span class="badge bg-<%=
                                progress.getOverallStatus().equals("Ready for Test") ? "success" :
                                progress.getOverallStatus().equals("Intermediate") ? "warning" : "danger" %>">
                                <%= progress.getOverallStatus() %>
                            </span>
                    </td>
                    <td><%= progress.getLessonsCompleted() %> lessons</td>
                    <td>
                        <%= progress.getSkills().values().stream().filter(b -> b).count() %> /
                        <%= progress.getSkills().size() %>
                    </td>
                    <td>
                        <div class="progress" style="height: 20px;">
                            <div class="progress-bar" role="progressbar"
                                 style="width: <%= progress.getProgressPercentage() %>%"
                                 aria-valuenow="<%= progress.getProgressPercentage() %>"
                                 aria-valuemin="0" aria-valuemax="100">
                            </div>
                        </div>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/progress/report?studentId=<%= progress.getStudentId() %>"
                           class="btn btn-sm btn-info">View</a>
                        <a href="${pageContext.request.contextPath}/progress/update?studentId=<%= progress.getStudentId() %>"
                           class="btn btn-sm btn-warning">Update</a>
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