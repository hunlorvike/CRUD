package hung.learn.crud.controllers;

import hung.learn.crud.configs.DatabaseProvider;
import hung.learn.crud.models.Teacher;
import hung.learn.crud.repositories.TeacherRepository;
import hung.learn.crud.common.utils.JspUtil;
import hung.learn.crud.common.utils.PasswordUtil;
import hung.learn.crud.common.utils.ValidateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static hung.learn.crud.common.Const.Views.*;

@WebServlet(
        name = "authController",
        urlPatterns = {"/auth"},
        asyncSupported = true,
        description = "Auth"
)
public class AuthController extends HttpServlet {
    private TeacherRepository teacherRepository;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseProvider.getConnection();
            teacherRepository = new TeacherRepository(connection);
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "signin";

        switch (action) {
            case "signout":
                handleSignOut(req, resp);
                break;
            case "signup":
                JspUtil.forwardToPage(req, resp, SIGNUP_PAGE);
                break;
            case "signin":
            default:
                JspUtil.forwardToPage(req, resp, SIGNIN_PAGE);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "signin";

        switch (action) {
            case "signup":
                handleSignUp(request, response);
                break;
            case "signin":
                handleSignIn(request, response);
                break;
            default:
                JspUtil.forwardToPage(request, response, SIGNIN_PAGE);
                break;
        }
    }

    private void handleSignUp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        boolean hasError = ValidateUtil.Auth.validateSignUpForm(request, username, password, name);

        if (hasError) {
            JspUtil.setRequestAttribute(request, "name", name);
            JspUtil.setRequestAttribute(request, "username", username);
            JspUtil.forwardToPage(request, response, SIGNUP_PAGE);
            return;
        }

        try {
            if (teacherRepository.findByUsername(username).isPresent()) {
                JspUtil.setRequestAttribute(request, "errorUsername", "Username already exists.");
                JspUtil.setRequestAttribute(request, "name", name);
                JspUtil.setRequestAttribute(request, "username", username);
                JspUtil.forwardToPage(request, response, SIGNUP_PAGE);
                return;
            }

            String hashedPassword = PasswordUtil.hash(password);
            Teacher newTeacher = Teacher.builder()
                    .fullname(name)
                    .username(username)
                    .password(hashedPassword)
                    .build();

            teacherRepository.add(newTeacher);

            JspUtil.setRequestAttribute(request, "toastMessage", "Sign-up successful! Please log in.");
            JspUtil.setRequestAttribute(request, "toastType", "success");
            JspUtil.forwardToPage(request, response, SIGNIN_PAGE);
        } catch (SQLException e) {
            throw new ServletException("Error during sign up process", e);
        }
    }

    private void handleSignIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate form input
        boolean hasError = ValidateUtil.Auth.validateSignInForm(request, username, password);

        if (hasError) {
            JspUtil.forwardToPage(request, response, SIGNIN_PAGE);
            return;
        }

        try {
            Optional<Teacher> teacherOptional = teacherRepository.findByUsername(username);

            if (teacherOptional.isPresent()) {
                Teacher teacher = teacherOptional.get();

                if (PasswordUtil.check(password, teacher.getPassword())) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", teacher);

                    JspUtil.redirectToPage(response, request.getContextPath() + "/");
                } else {
                    JspUtil.setRequestAttribute(request, "errorPassword", "Invalid credentials.");
                    JspUtil.forwardToPage(request, response, SIGNIN_PAGE);
                }
            } else {
                JspUtil.setRequestAttribute(request, "errorUsername", "User not found.");
                JspUtil.forwardToPage(request, response, SIGNIN_PAGE);
            }
        } catch (SQLException e) {
            throw new ServletException("Error during sign in process", e);
        }
    }


    private void handleSignOut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Failed to close the database connection: " + e.getMessage());
        }
    }
}
