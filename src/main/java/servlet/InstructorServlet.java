package servlet;

import service.InstructorService;
import service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InstructorServlet extends HttpServlet {
    private InstructorService instructorService;

    @Override
    public void init() throws ServletException {
        super.init();
        UserService userService = new UserService();
        this.instructorService = new InstructorService(userService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/register":
                registerForm(request, response);
                break;
            case "/list":
                listInstructors(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/error");
                break;
        }
    }

    private void registerForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/registerInstructor.jsp").forward(request, response);
    }

    private void listInstructors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        if (keyword != null && !keyword.trim().isEmpty()) {
            request.setAttribute("instructors", instructorService.searchInstructors(keyword));
        } else {
            request.setAttribute("instructors", instructorService.getAllInstructors());
        }
        request.getRequestDispatcher("/WEB-INF/views/listInstructors.jsp").forward(request, response);
    }
}
