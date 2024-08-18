package hung.learn.crud.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public interface _BaseAction {
    void get(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ServletException;
    void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    void delete(HttpServletRequest req, HttpServletResponse resp);
}
