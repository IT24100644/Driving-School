<%@ page import="model.Review" %>
<%@ page import="java.util.List" %>
<%@ page import="model.InstructorReview" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Review Moderation - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .rating-stars {
            color: #ffc107;
        }
    </style>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Review Moderation Panel</h2>
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
                    <th>Type</th>
                    <th>Student</th>
                    <th>Rating</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% List<Review> reviews = (List<Review>) request.getAttribute("reviews"); %>
                <% for (Review review : reviews) { %>
                <tr>
                    <td><%= review.getReviewId() %></td>
                    <td>
                        <% if (review instanceof InstructorReview) { %>
                        Instructor
                        <% } else { %>
                        Lesson
                        <% } %>
                    </td>
                    <td><%= reviewService.getStudentName(review.getStudentId()) %></td>
                    <td>
                        <div class="rating-stars">
                            <% for (int i = 1; i <= 5; i++) { %>
                            <i class="bi <%= i <= review.getRating() ? "bi-star-fill" : "bi-star" %>"></i>
                            <% } %>
                        </div>
                    </td>
                    <td><%= review.getReviewDate() %></td>
                    <td>
                        <% if (review.isEdited()) { %>
                        <span class="badge bg-secondary">Edited</span>
                        <% } else { %>
                        <span class="badge bg-success">Original</span>
                        <% } %>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/review/edit?id=<%= review.getReviewId() %>"
                           class="btn btn-sm btn-warning">Edit</a>
                        <form action="${pageContext.request.contextPath}/review/delete" method="post"
                              style="display: inline;">
                            <input type="hidden" name="id" value="<%= review.getReviewId() %>">
                            <button type="submit" class="btn btn-sm btn-danger"
                                    onclick="return confirm('Are you sure you want to delete this review?')">Delete</button>
                        </form>
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