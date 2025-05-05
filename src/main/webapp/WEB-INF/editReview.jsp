<%@ page import="model.Review" %>
<%@ page import="model.Instructor" %>
<%@ page import="model.Lesson" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Review - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .rating-star {
            cursor: pointer;
            font-size: 2rem;
            color: #ddd;
        }
        .rating-star.selected {
            color: #ffc107;
        }
    </style>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h4 class="text-center">Edit Review</h4>
                </div>
                <div class="card-body">
                    <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger">${param.error}</div>
                    <% } %>

                    <% Review review = (Review) request.getAttribute("review"); %>
                    <h5>
                        <% if (review instanceof InstructorReview) { %>
                        <% Instructor instructor = (Instructor) request.getAttribute("instructor"); %>
                        Review for Instructor: <%= instructor.getName() %>
                        <% } else { %>
                        <% Lesson lesson = (Lesson) request.getAttribute("lesson"); %>
                        Review for Lesson on <%= lesson.getStartTime().toLocalDate() %>
                        <% } %>
                    </h5>

                    <form action="${pageContext.request.contextPath}/review/update" method="post">
                        <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">

                        <div class="mb-4">
                            <label class="form-label">Rating</label>
                            <div class="d-flex justify-content-center mb-2">
                                <% for (int i = 1; i <= 5; i++) { %>
                                <i class="bi bi-star-fill rating-star <%= i <= review.getRating() ? "selected" : "" %>"
                                   data-value="<%= i %>"></i>
                                <% } %>
                            </div>
                            <input type="hidden" name="rating" id="ratingValue" value="<%= review.getRating() %>" required>
                        </div>

                        <div class="mb-3">
                            <label for="comment" class="form-label">Your Review</label>
                            <textarea class="form-control" id="comment" name="comment" rows="5" required><%= review.getComment() %></textarea>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Update Review</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const stars = document.querySelectorAll('.rating-star');
        const ratingInput = document.getElementById('ratingValue');

        stars.forEach(star => {
            star.addEventListener('click', function() {
                const value = parseInt(this.getAttribute('data-value'));
                ratingInput.value = value;

                stars.forEach(s => {
                    s.classList.remove('selected');
                    if (parseInt(s.getAttribute('data-value')) <= value) {
                        s.classList.add('selected');
                    }
                });
            });
        });
    });
</script>
<%@ include file="/footer.jsp" %>
</body>
</html>