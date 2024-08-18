package hung.learn.crud.common.utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class JspUtil {

    public static void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    public static void redirectToPage(HttpServletResponse response, String url) throws IOException {
        response.sendRedirect(url);
    }

    public static void setRequestAttribute(HttpServletRequest request, String name, Object value) {
        request.setAttribute(name, value);
    }

    public static void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage, String errorPage)
            throws ServletException, IOException {
        setRequestAttribute(request, "error", errorMessage);
        forwardToPage(request, response, errorPage);
    }
}
