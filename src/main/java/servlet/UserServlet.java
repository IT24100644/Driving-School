package servlet;

import model.*;
import service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/user/*")
public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        switch (action) {
            case "/register":
                showRegisterForm(request, response);
                break;
            case "/login":
                showLoginForm(request, response);
                break;
            case "/list":
                listUsers(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/profile":
                showProfile(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        switch (action) {
            case "/register":
                registerUser(request, response);
                break;
            case "/login":
                loginUser(request, response);
                break;
            case "/update":
                updateUser(request, response);
                break;
            case "/delete":
                deleteUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
    }

    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/views/admin/user-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");
        User user = userService.getUserById(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/views/auth/update-profile.jsp").forward(request, response);
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = ((User)request.getSession().getAttribute("user")).getId();
        User user = userService.getUserById(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/views/auth/profile.jsp").forward(request, response);
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = request.getParameter("role");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user;
        if ("INSTRUCTOR".equals(role)) {
            user = new Instructor();
            ((Instructor)user).setExperienceYears(0);
            ((Instructor)user).setCertified(false);
        } else {
            user = new Student();
            ((Student)user).setLicenseNumber("");
        }

        user.setId(generateUserId(role));
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        if (userService.registerUser(user)) {
            response.sendRedirect(request.getContextPath() + "/user/login?success=Registration successful");
        } else {
            response.sendRedirect(request.getContextPath() + "/user/register?error=Email already exists");
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.authenticate(email, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if ("INSTRUCTOR".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/instructor/dashboard");
            } else if ("STUDENT".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/student/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/user/login?error=Invalid credentials");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");
        User existingUser = userService.getUserById(userId);

        if (existingUser != null) {
            existingUser.setName(request.getParameter("name"));
            existingUser.setPhone(request.getParameter("phone"));
            existingUser.setAddress(request.getParameter("address"));

            if (existingUser instanceof Instructor) {
                ((Instructor)existingUser).setCertified(
                        Boolean.parseBoolean(request.getParameter("certified")));
            } else if (existingUser instanceof Student) {
                ((Student)existingUser).setLicenseNumber(
                        request.getParameter("licenseNumber"));
            }

            if (userService.updateUser(existingUser)) {
                request.getSession().setAttribute("user", existingUser);
                response.sendRedirect(request.getContextPath() + "/user/profile?success=Profile updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/user/edit?id=" + userId + "&error=Update failed");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/user/list?error=User not found");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");
        if (userService.deleteUser(userId)) {
            response.sendRedirect(request.getContextPath() + "/user/list?success=User deleted");
        } else {
            response.sendRedirect(request.getContextPath() + "/user/list?error=Delete failed");
        }
    }

    private String generateUserId(String role) {
        return role.charAt(0) + "-" + System.currentTimeMillis();
    }
}
