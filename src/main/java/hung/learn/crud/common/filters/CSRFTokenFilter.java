package hung.learn.crud.common.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@WebFilter(
        urlPatterns = {"/*"}
)
public class CSRFTokenFilter implements Filter {

    private static final String CSRF_TOKEN_ATTRIBUTE = "csrfToken";
    private static final String CSRF_TOKEN_SESSION_ATTRIBUTE = "csrfTokenSession";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        // Generate and store CSRF token in session
        String csrfToken = (String) session.getAttribute(CSRF_TOKEN_SESSION_ATTRIBUTE);
        if (csrfToken == null) {
            csrfToken = generateCSRFToken();
            session.setAttribute(CSRF_TOKEN_SESSION_ATTRIBUTE, csrfToken);
        }

        // Add CSRF token to request attributes
        request.setAttribute(CSRF_TOKEN_ATTRIBUTE, csrfToken);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String generateCSRFToken() {
        SecureRandom random = new SecureRandom();
        byte[] token = new byte[32];
        random.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}

