package com.library.demo.middleware;

import com.library.demo.model.Log;
import com.library.demo.repository.LogRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final LogRepository logRepository;

    // Constructor to inject LogRepository
    public RequestLoggingFilter(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Long patronId = (Long) request.getSession().getAttribute("patronId"); // Retrieve patron ID from session
        String ipAddress = request.getRemoteAddr();
        String endpoint = request.getRequestURI();
        String httpMethod = request.getMethod(); // Capture the HTTP method

        // Continue the filter chain
        filterChain.doFilter(request, response);

        // Create a log entry
        Log logEntry = new Log();
        logEntry.setPatronId(patronId);
        logEntry.setIpAddress(ipAddress);
        logEntry.setEndpoint(endpoint);
        logEntry.setHttpMethod(httpMethod); // Set the HTTP method in the log
        logEntry.setTimestamp(LocalDateTime.now());

        // Save the log entry
        logRepository.save(logEntry);
    }
}
