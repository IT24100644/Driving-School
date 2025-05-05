<%@ page import="model.InstructorReview" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Instructor Reviews - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .review-card {
            margin-bottom: 20px;
            border-left: 4px solid #ffc107;
        }
        .rating-stars {
            color: #ffc107;
        }
    </style>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="card mb-4">
        <div class="card-body text-center">
            <h2><%= request.getAttribute("instructorName") %></h2>
            <div class="display-4"><%= String.format("%.1f", request.getAttribute("averageRating")) %></div>
            <div class="rating-stars mb-2">
                <% int avgRating = (int)Math.round((double)request.getAttribute("averageRating")); %>
                <% for (int i = 1; i <= 5; i++) { %>
                <i class="bi <%= i <= avgRating ? "bi-star-fill" : "bi-star" %>"></i>
                <% } %>
            </div>
            <p class="text-muted">Based on <%= ((List<?>)request.getAttribute("reviews")).size() %> reviews</p>
        </div>
    </div>

    <% if (request.getParameter("success") != null) { %>
    <div class="alert alert-success">${param.success}</div>
    <% } %>

    <div class="row">
        <div class="col-md-8 mx-auto">
            <% List<InstructorReview> reviews = (List<InstructorReview>) request.getAttribute("reviews"); %>
            <% if (reviews.isEmpty()) { %>
            <div class="alert alert-info">No reviews yet for this instructor.</div>
            <% } else { %>
            <% for (InstructorReview review : reviews) { %>
            <div class="card review-card">
                <div class="card-body">
                    <div class="d-flex justify-content-between mb-2">
                        <h5><%= reviewService.getStudentName(review.getStudentId()) %></h5>
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

                    <% if (session.getAttribute("user") != null &&
                            ((User)session.getAttribute("user")).getId().equals(review.getStudentId())) { %>
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
                    <% } %>
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