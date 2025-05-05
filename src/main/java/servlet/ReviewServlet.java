package servlet;

import model.*;
import service.ReviewService;
import service.UserService;
import service.LessonService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReviewServlet", value = "/review/*")
public class ReviewServlet extends HttpServlet {
    private ReviewService reviewService;

    @Override
    public void init() throws ServletException {
        super.init();
        UserService userService = new UserService();
        LessonService lessonService = new LessonService(userService);
        this.reviewService = new ReviewService(userService, lessonService);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        switch (action) {
            case "/submit":
                showSubmitForm(request, response);
                break;
            case "/instructor":
                showInstructorReviews(request, response);
                break;
            case "/lesson":
                showLessonReviews(request, response);
                break;
            case "/myreviews":
                showStudentReviews(request, response);
                break;
            case "/admin":
                showAdminPanel(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        switch (action) {
            case "/submit":
                submitReview(request, response);
                break;
            case "/update":
                updateReview(request, response);
                break;
            case "/delete":
                deleteReview(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showSubmitForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user instanceof Student) {
            String reviewType = request.getParameter("type");
            String entityId = request.getParameter("id");

            if ("instructor".equals(reviewType)) {
                Instructor instructor = (Instructor) reviewService.getUserById(entityId);
                if (instructor != null) {
                    request.setAttribute("instructor", instructor);
                    request.setAttribute("reviewType", "instructor");
                    request.getRequestDispatcher("/views/review/submit-review.jsp").forward(request, response);
                    return;
                }
            } else if ("lesson".equals(reviewType)) {
                Lesson lesson = reviewService.getLessonById(entityId);
                if (lesson != null) {
                    request.setAttribute("lesson", lesson);
                    request.setAttribute("reviewType", "lesson");
                    request.getRequestDispatcher("/views/review/submit-review.jsp").forward(request, response);
                    return;
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/error/400.jsp");
    }

    private void showInstructorReviews(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String instructorId = request.getParameter("id");
        List<InstructorReview> reviews = reviewService.getReviewsForInstructor(instructorId);
        double averageRating = reviewService.getAverageRatingForInstructor(instructorId);

        request.setAttribute("reviews", reviews);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("instructorName", reviewService.getInstructorName(instructorId));
        request.getRequestDispatcher("/views/review/instructor-reviews.jsp").forward(request, response);
    }

    private void showLessonReviews(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lessonId = request.getParameter("id");
        List<LessonReview> reviews = reviewService.getReviewsForLesson(lessonId);

        request.setAttribute("reviews", reviews);
        request.setAttribute("lessonDescription", reviewService.getLessonDescription(lessonId));
        request.getRequestDispatcher("/views/review/lesson-reviews.jsp").forward(request, response);
    }

    private void showStudentReviews(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("user");

        List<Review> reviews = reviewService.getReviewsByStudent(student.getId());
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("/views/review/student-reviews.jsp").forward(request, response);
    }

    private void showAdminPanel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Review> allReviews = reviewService.getAllReviews();
        request.setAttribute("reviews", allReviews);
        request.getRequestDispatcher("/views/review/admin-panel.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reviewId = request.getParameter("id");
        Review review = reviewService.getReviewById(reviewId);

        if (review != null) {
            request.setAttribute("review", review);
            if (review instanceof InstructorReview) {
                request.setAttribute("instructor",
                        reviewService.getUserById(((InstructorReview)review).getInstructorId()));
            } else if (review instanceof LessonReview) {
                request.setAttribute("lesson",
                        reviewService.getLessonById(((LessonReview)review).getLessonId()));
            }
            request.getRequestDispatcher("/views/review/edit-review.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/review/myreviews?error=Review not found");
        }
    }

    private void submitReview(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("user");

        String reviewType = request.getParameter("reviewType");
        String entityId = request.getParameter("entityId");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        Review review;
        if ("instructor".equals(reviewType)) {
            review = new InstructorReview();
            ((InstructorReview)review).setInstructorId(entityId);
        } else {
            review = new LessonReview();
            ((LessonReview)review).setLessonId(entityId);
        }

        review.setReviewId(generateReviewId(reviewType));
        review.setStudentId(student.getId());
        review.setRating(rating);
        review.setComment(comment);

        if (reviewService.submitReview(review)) {
            response.sendRedirect(request.getContextPath() + "/review/myreviews?success=Review submitted");
        } else {
            response.sendRedirect(request.getContextPath() + "/review/submit?type=" + reviewType + "&id=" + entityId + "&error=Submission failed");
        }
    }

    private void updateReview(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reviewId = request.getParameter("reviewId");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        Review review = reviewService.getReviewById(reviewId);
        if (review != null) {
            review.setRating(rating);
            review.setComment(comment);

            if (reviewService.updateReview(review)) {
                response.sendRedirect(request.getContextPath() + "/review/myreviews?success=Review updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/review/edit?id=" + reviewId + "&error=Update failed");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/review/myreviews?error=Review not found");
        }
    }

    private void deleteReview(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reviewId = request.getParameter("id");

        if (reviewService.deleteReview(reviewId)) {
            response.sendRedirect(request.getContextPath() + "/review/myreviews?success=Review deleted");
        } else {
            response.sendRedirect(request.getContextPath() + "/review/myreviews?error=Deletion failed");
        }
    }

    private String generateReviewId(String reviewType) {
        return "REV-" + reviewType.toUpperCase().charAt(0) + "-" + System.currentTimeMillis();
    }
}
