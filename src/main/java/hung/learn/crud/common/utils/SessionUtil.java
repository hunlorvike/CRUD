package hung.learn.crud.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    public static String getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (String) session.getAttribute("user");
        }
        return null;
    }

    public static boolean isUserLoggedIn(HttpServletRequest request) {
        return getUserFromSession(request) != null;
    }

    public static void logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public static void setSessionAttribute(HttpServletRequest request, String name, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(name, value);
    }

    public static Object getSessionAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession();
        return session.getAttribute(name);
    }

    public static void removeSessionAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession();
        session.removeAttribute(name);
    }
}
