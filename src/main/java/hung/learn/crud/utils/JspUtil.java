package hung.learn.crud.utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JspUtil {

    // Chuyển tiếp (forward) yêu cầu đến một trang JSP
    public static void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    // Chuyển hướng (redirect) người dùng đến một URL khác
    public static void redirectToPage(HttpServletResponse response, String url) throws IOException {
        response.sendRedirect(url);
    }
}
