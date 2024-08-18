package hung.learn.crud.controllers;

import hung.learn.crud.common.utils.JspUtil;
import hung.learn.crud.configs.DatabaseProvider;
import hung.learn.crud.models.Student;
import hung.learn.crud.repositories.StudentRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static hung.learn.crud.common.Const.Views.INDEX_PAGE;
import static hung.learn.crud.common.Const.Views.NOT_FOUND_PAGE;

@WebServlet(
        name = "indexController",
        urlPatterns = {"/index", "/not-found"},
        loadOnStartup = 1,
        asyncSupported = true,
        description = "Home"
)
public class IndexController extends HttpServlet {
    private StudentRepository studentRepository;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseProvider.getConnection();
            studentRepository = new StudentRepository(connection);
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
            case "/index":
                try {
                    List<Student> students = studentRepository.findAll();
                    req.setAttribute("students", students);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                JspUtil.forwardToPage(req, resp, INDEX_PAGE);
                break;
            case "/not-found":
                JspUtil.forwardToPage(req, resp, NOT_FOUND_PAGE);
                break;
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
