package hung.learn.crud.controllers;

import hung.learn.crud.common._BaseAction;
import hung.learn.crud.common.utils.JspUtil;
import hung.learn.crud.common.utils.ValidateUtil;
import hung.learn.crud.configs.database.DatabaseProvider;
import hung.learn.crud.models.Student;
import hung.learn.crud.repositories.StudentRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static hung.learn.crud.common.Const.Views.*;

@WebServlet(
        name = "studentController",
        urlPatterns = {"/add-student", "/edit-student", "/delete-student", "/detail-student"},
        asyncSupported = true,
        description = "Student"
)
public class StudentController extends HttpServlet {
    private StudentRepository studentRepository;
    private Connection connection;
    private final Action studentAction = new Action();

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseProvider.getConnection();
            studentRepository = new StudentRepository(connection);
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getServletPath();

        switch (path) {
            case "/add-student":
                JspUtil.forwardToPage(req, resp, ADD_STUDENT_PAGE);
                break;
            case "/edit-student":
            case "/delete-student":
                studentAction.get(req, resp, path);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
            case "/add-student":
                studentAction.add(req, resp);
                break;
            case "/edit-student":
                studentAction.edit(req, resp);
                break;
            case "/delete-student":
                studentAction.delete(req, resp);
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

    private class Action implements _BaseAction {

        public void get(HttpServletRequest req, HttpServletResponse resp, String mode) throws IOException, SQLException, ServletException {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                JspUtil.redirectToPage(resp, NOT_FOUND_PAGE);
                return;
            }

            try {
                int id = Integer.parseInt(idParam);
                Optional<Student> student = studentRepository.findById(id);
                if (student.isPresent()) {
                    JspUtil.setRequestAttribute(req, "student", student.get());
                    switch (mode) {
                        case "/edit-student":
                            JspUtil.forwardToPage(req, resp, EDIT_STUDENT_PAGE);
                            break;
                        case "/delete-student":
                            JspUtil.forwardToPage(req, resp, DELETE_STUDENT_PAGE);
                            break;
                    }
                } else {
                    JspUtil.forwardToPage(req, resp, NOT_FOUND_PAGE);
                }
            } catch (NumberFormatException e) {
                JspUtil.forwardToPage(req, resp, NOT_FOUND_PAGE);
            }
        }

        @Override
        public void get(HttpServletRequest req, HttpServletResponse resp) {
        }

        @Override
        public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String fullName = req.getParameter("fullname");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            String pointStr = req.getParameter("point");

            boolean hasError = ValidateUtil.Student.validateAddOrEditForm(req, fullName, phone, address, pointStr);

            if (hasError) {
                JspUtil.setRequestAttribute(req, "fullname", fullName);
                JspUtil.setRequestAttribute(req, "phone", phone);
                JspUtil.setRequestAttribute(req, "address", address);
                JspUtil.setRequestAttribute(req, "point", pointStr);
                JspUtil.forwardToPage(req, resp, ADD_STUDENT_PAGE);
                return;
            }

            try {
                Student newStudent = Student.builder()
                        .fullname(fullName)
                        .phone(phone)
                        .address(address)
                        .point(Float.parseFloat(pointStr))
                        .build();

                studentRepository.add(newStudent);
                JspUtil.setRequestAttribute(req, "toastMessage", "Create student successful.");
                JspUtil.setRequestAttribute(req, "toastType", "success");
                JspUtil.redirectToPage( resp, "/");
            } catch (SQLException e) {
                throw new ServletException("Error during sign up process", e);
            }
        }

        @Override
        public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String idParam = req.getParameter("id");
            String fullName = req.getParameter("fullname");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            String pointStr = req.getParameter("point");

            boolean hasError = ValidateUtil.Student.validateAddOrEditForm(req, fullName, phone, address, pointStr);

            if (hasError) {
                JspUtil.setRequestAttribute(req, "id", idParam);
                JspUtil.setRequestAttribute(req, "fullname", fullName);
                JspUtil.setRequestAttribute(req, "phone", phone);
                JspUtil.setRequestAttribute(req, "address", address);
                JspUtil.setRequestAttribute(req, "point", pointStr);
                JspUtil.forwardToPage(req, resp, EDIT_STUDENT_PAGE);
                return;
            }

            try {
                int id = Integer.parseInt(idParam);
                Student updatedStudent = Student.builder()
                        .id(id)
                        .fullname(fullName)
                        .phone(phone)
                        .address(address)
                        .point(Float.parseFloat(pointStr))
                        .build();

                studentRepository.update(updatedStudent);
                JspUtil.setRequestAttribute(req, "toastMessage", "Update student successful.");
                JspUtil.setRequestAttribute(req, "toastType", "success");
                JspUtil.redirectToPage( resp, "/");
            } catch (SQLException e) {
                throw new ServletException("Error during update process", e);
            } catch (NumberFormatException e) {
                JspUtil.forwardToPage(req, resp, NOT_FOUND_PAGE);
            }
        }

        @SneakyThrows
        @Override
        public void delete(HttpServletRequest req, HttpServletResponse resp) {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                JspUtil.forwardToPage(req, resp, NOT_FOUND_PAGE);
                return;
            }

            try {
                int id = Integer.parseInt(idParam);
                studentRepository.delete(id);
                JspUtil.setRequestAttribute(req, "toastMessage", "Delete student successful.");
                JspUtil.setRequestAttribute(req, "toastType", "success");
                JspUtil.redirectToPage( resp, "/");
            } catch (SQLException e) {
                throw new ServletException("Error during delete process", e);
            } catch (NumberFormatException e) {
                JspUtil.forwardToPage(req, resp, NOT_FOUND_PAGE);
            }
        }
    }
}
