package servlet;

import service.LessonService;
import service.UserService;
import model.Lesson;
import model.Instructor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LessonServlet extends HttpServlet {
    private LessonService lessonService;

    @Override
    public void init() throws ServletException {
        super.init();
        UserService userService = new UserService();
        this.lessonService = new LessonService(userService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/view":
                viewLesson(request, response);
                break;
            case "/edit":
                editLessonForm(request, response);
                break;
            case "/schedule":
                scheduleLessonForm(request, response);
                break;
            case "/student":
                listLessonsByStudent(request, response);
                break;
            case "/instructor":
                listLessonsByInstructor(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/error");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/schedule":
                scheduleLesson(request, response);
                break;
            case "/update":
                updateLesson(request, response);
                break;
            case "/cancel":
                cancelLesson(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/error");
                break;
        }
    }

    private void viewLesson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonId = request.getParameter("id");
        Lesson lesson = lessonService.getLessonById(lessonId);
        if (lesson != null) {
            request.setAttribute("lesson", lesson);
            request.getRequestDispatcher("/WEB-INF/views/viewLesson.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/lesson/list?error=lesson_not_found");
        }
    }

    private void editLessonForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonId = request.getParameter("id");
        Lesson lesson = lessonService.getLessonById(lessonId);
        if (lesson != null) {
            request.setAttribute("lesson", lesson);
            request.getRequestDispatcher("/WEB-INF/views/editLesson.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/lesson/list?error=lesson_not_found");
        }
    }

    private void scheduleLessonForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateTimeStr = request.getParameter("dateTime");
        String durationStr = request.getParameter("duration");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(dateTimeStr, formatter);
        int duration = Integer.parseInt(durationStr);
        List<Instructor> availableInstructors = lessonService.getAvailableInstructors(startTime, duration);
        request.setAttribute("instructors", availableInstructors);
        request.setAttribute("startTime", startTime);
        request.setAttribute("duration", duration);
        request.getRequestDispatcher("/WEB-INF/views/scheduleLesson.jsp").forward(request, response);
    }

    private void listLessonsByStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        List<Lesson> lessons = lessonService.getLessonsByStudent(studentId);
        request.setAttribute("lessons", lessons);
        request.getRequestDispatcher("/WEB-INF/views/listLessons.jsp").forward(request, response);
    }

    private void listLessonsByInstructor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String instructorId = request.getParameter("instructorId");
        List<Lesson> lessons = lessonService.getLessonsByInstructor(instructorId);
        request.setAttribute("lessons", lessons);
        request.getRequestDispatcher("/WEB-INF/views/listLessons.jsp").forward(request, response);
    }

    private void scheduleLesson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonId = request.getParameter("id");
        String studentId = request.getParameter("studentId");
        String instructorId = request.getParameter("instructorId");
        String dateTimeStr = request.getParameter("dateTime");
        String durationStr = request.getParameter("duration");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(dateTimeStr, formatter);
        int duration = Integer.parseInt(durationStr);

        Lesson lesson = new Lesson();
        if (lessonService.scheduleLesson(lesson)) {
            response.sendRedirect(request.getContextPath() + "/lesson/list?success=scheduled");
        } else {
            response.sendRedirect(request.getContextPath() + "/lesson/schedule?error=schedule_failed");
        }
    }

    private void updateLesson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonId = request.getParameter("id");
        Lesson lesson = lessonService.getLessonById(lessonId);
        if (lesson != null) {
            String dateTimeStr = request.getParameter("dateTime");
            String durationStr = request.getParameter("duration");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(dateTimeStr, formatter);
            int duration = Integer.parseInt(durationStr);
            lesson.setStartTime(startTime);
            lesson.setDuration(duration);
            if (lessonService.updateLesson(lesson)) {
                response.sendRedirect(request.getContextPath() + "/lesson/view?id=" + lessonId + "&success=updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/lesson/edit?id=" + lessonId + "&error=update_failed");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/lesson/list?error=lesson_not_found");
        }
    }

    private void cancelLesson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lessonId = request.getParameter("id");
        if (lessonService.cancelLesson(lessonId)) {
            response.sendRedirect(request.getContextPath() + "/lesson/list?success=cancelled");
        } else {
            response.sendRedirect(request.getContextPath() + "/lesson/list?error=cancel_failed");
        }
    }
}