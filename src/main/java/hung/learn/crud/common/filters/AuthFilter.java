package hung.learn.crud.common.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@WebFilter(
        urlPatterns = {"/*"}
)
public class AuthFilter implements Filter {

    // List of patterns to bypass session validation
    private static final List<Pattern> EXCLUDED_PATTERNS = Arrays.asList(
            Pattern.compile("^/auth.*$"),      // Matches any path starting with /auth
            Pattern.compile("^/public.*$")     // Matches any path starting with /public
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        String requestURI = request.getRequestURI();

        boolean shouldBypass = EXCLUDED_PATTERNS.stream()
                .anyMatch(pattern -> pattern.matcher(requestURI).matches());

        if (shouldBypass) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (session != null && session.getAttribute("user") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(request.getContextPath() + "/auth?action=signin");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
