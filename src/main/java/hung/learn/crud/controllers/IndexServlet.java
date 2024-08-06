package hung.learn.crud.controllers;

import hung.learn.crud.configs.DatabaseConfig;
import hung.learn.crud.repositories.StudentRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "indexServlet", value = "/")
public class IndexServlet extends HttpServlet {

    private StudentRepository studentRepo;
    private Connection connection;
    @Override
    public void init() throws ServletException {
        try {
             connection = DatabaseConfig.getConnection();
            studentRepo = new StudentRepository(connection);
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    public void destroy() {
        DatabaseConfig.closeConnection(connection);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/index.jsp");
        dispatcher.forward(request, response);
    }
}
