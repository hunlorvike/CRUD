package hung.learn.crud.common.utils;

import jakarta.servlet.http.HttpServletRequest;

public class ValidateUtil {
    public static class Auth {
        public static boolean validateSignUpForm(HttpServletRequest request, String username, String password, String name) {
            boolean hasError = false;

            if (username == null || username.trim().isEmpty()) {
                JspUtil.setRequestAttribute(request, "errorUsername", "Username is required.");
                hasError = true;
            }

            if (password == null || password.trim().isEmpty()) {
                JspUtil.setRequestAttribute(request, "errorPassword", "Password is required.");
                hasError = true;
            }

            if (name == null || name.trim().isEmpty()) {
                JspUtil.setRequestAttribute(request, "errorName", "Name is required.");
                hasError = true;
            }

            return hasError;
        }

        public static boolean validateSignInForm(HttpServletRequest request, String username, String password) {
            boolean hasError = false;

            if (username == null || username.trim().isEmpty()) {
                JspUtil.setRequestAttribute(request, "errorUsername", "Username is required.");
                hasError = true;
            }

            if (password == null || password.trim().isEmpty()) {
                JspUtil.setRequestAttribute(request, "errorPassword", "Password is required.");
                hasError = true;
            }

            return hasError;
        }
    }

    public static class Student {
        public static boolean validateAddOrEditForm(HttpServletRequest request, String fullname, String phone, String address, String pointStr) {
            boolean hasError = false;

            if (fullname == null || fullname.trim().isEmpty()) {
                JspUtil.setRequestAttribute(request, "errorFullname", "Fullname is required");
                hasError = true;
            }

            if (phone == null || phone.trim().isEmpty()) {
                JspUtil.setRequestAttribute(request, "errorPhone", "Phone is required");
                hasError = true;
            }

            if (address == null || address.trim().isEmpty()) {
                JspUtil.setRequestAttribute(request, "errorAddress", "Address is required");
                hasError = true;
            }

            if (pointStr == null || pointStr.trim().isEmpty()) {
                JspUtil.setRequestAttribute(request, "errorPointStr", "Point is required");
                hasError = true;
            }

            return hasError;
        }
    }
}
