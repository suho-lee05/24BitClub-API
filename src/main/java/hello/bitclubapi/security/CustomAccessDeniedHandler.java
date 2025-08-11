package hello.bitclubapi.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        System.err.println("=== 403 Access Denied ===");
        System.err.println("Request URI: " + request.getRequestURI());
        System.err.println("Method: " + request.getMethod());
        System.err.println("Authorization: " + request.getHeader("Authorization"));
        System.err.println("Exception Message: " + accessDeniedException.getMessage());

        // 로그 찍은 후 403 응답
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: " + accessDeniedException.getMessage());
    }
}