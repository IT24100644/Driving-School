<%@ page import="model.Review" %>
<%@ page import="java.util.List" %>
<%@ page import="model.InstructorReview" %>
<%@ page import="model.LessonReview" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Reviews - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .review-card {
            margin-bottom: 20px;
            border-left: 4px solid #0d6efd;
        }
        .rating-stars {
            color: #ffc107;
        }
    </style>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>My Reviews</h2>
    </div>

    <% if (request.getParameter("success") != null) { %>
    <div class="alert alert-success">${param.success}</div>
    <% } %>
    <% if (request.getParameter("error") != null) { %>
    <div class="alert alert-danger">${param.error}</div>
    <% } %>

    <div class="row">
        <div class="col-md-10 mx-auto">
            <% List<Review> reviews = (List<Review>) request.getAttribute("reviews"); %>
            <% if (reviews.isEmpty()) { %>
            <div class="alert alert-info">You haven't submitted any reviews yet.</div>
            <% } else { %>
            <% for (Review review : reviews) { %>
            <div class="card review-card">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <h5>
                            <% if (review instanceof InstructorReview) { %>
                            Review for Instructor: <%= reviewService.getInstructorName(((InstructorReview)review).getInstructorId()) %>
                            <% } else { %>
                            Review for Lesson: <%= reviewService.getLessonDescription(((LessonReview)review).getLessonId()) %>
                            <% } %>
                        </h5>
                        <div class="rating-stars">
                            <% for (int i = 1; i <= 5; i++) { %>
                            <i class="bi <%= i <= review.getRating() ? "bi-star-fill" : "bi-star" %>"></i>
                            <% } %>
                        </div>
                    </div>
                    <p class="text-muted">
                        <%= review.getReviewDate() %>
                        <% if (review.isEdited()) { %>
                        <span class="badge bg-secondary">Edited</span>
                        <% } %>
                    </p>
                    <p><%= review.getComment() %></p>

                    <div class="mt-2">
                        <a href="${pageContext.request.contextPath}/review/edit?id=<%= review.getReviewId() %>"
                           class="btn btn-sm btn-outline-warning">Edit</a>
                        <form action="${pageContext.request.contextPath}/review/delete" method="post"
                              style="display: inline;">
                            <input type="hidden" name="id" value="<%= review.getReviewId() %>">
                            <button type="submit" class="btn btn-sm btn-outline-danger"
                                    onclick="return confirm('Are you sure?')">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
            <% } %>
            <% } %>
        </div>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>