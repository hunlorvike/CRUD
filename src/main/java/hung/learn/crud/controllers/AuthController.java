package hung.learn.crud.controllers;

import hung.learn.crud.configs.DatabaseConfig;
import hung.learn.crud.models.User;
import hung.learn.crud.repositories.UserRepository;
import hung.learn.crud.utils.JspUtil;
import hung.learn.crud.utils.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static hung.learn.crud.common.Const.*;

@WebServlet(name = "authController", value = "/auth")
public class AuthController extends HttpServlet {
    private UserRepository userRepository;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseConfig.getConnection();
            userRepository = new UserRepository(connection);
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch (action != null ? action : "signin") {
            case "signup":
                JspUtil.forwardToPage(req, resp, SIGNUP_PAGE);
                break;
            case "signin":
            default:
                JspUtil.forwardToPage(req, resp, LOGIN_PAGE);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action != null ? action : "signin") {
            case "signup":
                handleSignUp(request, response);
                break;
            case "signin":
                handleSignIn(request, response);
                break;
            default:
                JspUtil.redirectToPage(response, LOGIN_PAGE);
                break;
        }
    }

    private void handleSignUp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        try {
            Optional<User> userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                request.setAttribute("error", "Username already exists.");
                JspUtil.forwardToPage(request, response, SIGNUP_PAGE);
                return;
            }

            String hashedPassword = PasswordUtil.hash(password);
            User newUser = new User(0, username, hashedPassword, name);
            userRepository.add(newUser);

            JspUtil.redirectToPage(response, "auth?action=signin&message=Sign up successful! You can now log in.");
        } catch (SQLException e) {
            throw new ServletException("Error during sign up process", e);
        }
    }

    private void handleSignIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Optional<User> userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (PasswordUtil.check(password, user.getPassword())) {
                    request.getSession().setAttribute("user", username);
                    JspUtil.redirectToPage(response, INDEX_PAGE);
                } else {
                    request.setAttribute("error", "Invalid credentials.");
                    JspUtil.forwardToPage(request, response, LOGIN_PAGE);
                }
            } else {
                request.setAttribute("error", "User not found.");
                JspUtil.forwardToPage(request, response, LOGIN_PAGE);
            }
        } catch (SQLException e) {
            throw new ServletException("Error during sign in process", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
