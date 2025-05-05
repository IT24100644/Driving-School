package servlet;

import model.*;
import service.ProgressService;
import service.UserService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ProgressServlet", value = "/progress/*")
public class ProgressServlet extends HttpServlet {
    private ProgressService progressService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.progressService = new ProgressService();
        this.userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        switch (action) {
            case "/dashboard":
                showDashboard(request, response);
                break;
            case "/report":
                showProgressReport(request, response);
                break;
            case "/update":
                showUpdateForm(request, response);
                break;
            case "/feedback":
                showFeedbackForm(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        switch (action) {
            case "/feedback":
                submitFeedback(request, response);
                break;
            case "/update":
                updateProgress(request, response);
                break;
            case "/delete":
                deleteProgress(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user instanceof Student) {
            Progress progress = progressService.getProgressByStudentId(user.getId());
            request.setAttribute("progress", progress);
            request.getRequestDispatcher("/views/progress/student-dashboard.jsp").forward(request, response);
        } else if (user instanceof Instructor) {
            // Get all students assigned to this instructor
            List<Student> students = userService.getAllUsers().stream()
                    .filter(u -> u instanceof Student)
                    .map(u -> (Student) u)
                    .collect(Collectors.toList());

            List<Progress> progressList = progressService.getProgressByInstructor(
                    students.stream().map(Student::getId).collect(Collectors.toList()));

            request.setAttribute("progressList", progressList);
            request.getRequestDispatcher("/views/progress/instructor-dashboard.jsp").forward(request, response);
        } else {
            // Admin view
            List<Progress> allProgress = progressService.getAllProgress();
            request.setAttribute("progressList", allProgress);
            request.getRequestDispatcher("/views/progress/admin-dashboard.jsp").forward(request, response);
        }
    }

    private void showProgressReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        Progress progress = progressService.getProgressByStudentId(studentId);

        if (progress != null) {
            Student student = (Student) userService.getUserById(studentId);
            request.setAttribute("progress", progress);
            request.setAttribute("student", student);
            request.getRequestDispatcher("/views/progress/report.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/progress/dashboard?error=Student not found");
        }
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        Progress progress = progressService.getProgressByStudentId(studentId);

        if (progress != null) {
            request.setAttribute("progress", progress);
            request.setAttribute("skills", Arrays.asList(
                    "Steering Control", "Acceleration", "Braking", "Gear Changing",
                    "Mirror Usage", "Signaling", "Parking", "Lane Changing",
                    "Roundabouts", "Emergency Stop", "Reversing", "Hill Start"
            ));
            request.getRequestDispatcher("/views/progress/update-form.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/progress/dashboard?error=Student not found");
        }
    }

    private void showFeedbackForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String lessonId = request.getParameter("lessonId");

        Student student = (Student) userService.getUserById(studentId);
        if (student != null) {
            request.setAttribute("student", student);
            request.setAttribute("lessonId", lessonId);
            request.getRequestDispatcher("/views/progress/feedback-form.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/progress/dashboard?error=Student not found");
        }
    }

    private void submitFeedback(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String lessonId = request.getParameter("lessonId");
        String feedback = request.getParameter("feedback");

        if (progressService.addLessonFeedback(studentId, lessonId, feedback)) {
            response.sendRedirect(request.getContextPath() + "/progress/report?studentId=" + studentId + "&success=Feedback submitted");
        } else {
            response.sendRedirect(request.getContextPath() + "/progress/feedback?studentId=" + studentId + "&lessonId=" + lessonId + "&error=Submission failed");
        }
    }

    private void updateProgress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String[] skills = request.getParameterValues("skills");
        String[] mastered = request.getParameterValues("mastered");

        Progress progress = progressService.getProgressByStudentId(studentId);
        if (progress != null) {
            // Update skills
            if (skills != null && mastered != null) {
                for (int i = 0; i < skills.length; i++) {
                    progress.markSkill(skills[i], "on".equals(mastered[i]));
                }
            }

            if (progressService.saveProgress(progress)) {
                response.sendRedirect(request.getContextPath() + "/progress/report?studentId=" + studentId + "&success=Progress updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/progress/update?studentId=" + studentId + "&error=Update failed");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/progress/dashboard?error=Student not found");
        }
    }

    private void deleteProgress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentId = request.getParameter("studentId");

        if (progressService.deleteProgress(studentId)) {
            response.sendRedirect(request.getContextPath() + "/progress/dashboard?success=Progress record deleted");
        } else {
            response.sendRedirect(request.getContextPath() + "/progress/report?studentId=" + studentId + "&error=Deletion failed");
        }
    }
}
