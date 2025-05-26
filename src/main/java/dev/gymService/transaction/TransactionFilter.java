package dev.gymService.transaction;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@WebFilter("/*")
public class TransactionFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // Check if the transactionId is already set in the request headers
        String transactionId = httpRequest.getHeader("transactionId");
        if (transactionId == null) {
            transactionId = UUID.randomUUID().toString();
        }

        try {
            // Add the transactionId to MDC for logging
            MDC.put("transactionId", transactionId);

            // Log the rest call details: URL | request type
            logger.info(" | URL: {} | Request type: {}",
                    httpRequest.getRequestURL(),
                    httpRequest.getMethod());

            // Continue with the request processing
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (IOException | ServletException | IllegalArgumentException e) {
            logger.error("IllegalArgumentException exception has been thrown " + e.getMessage());
        } finally {
            // Clean up MDC after the request is processed
            MDC.remove("transactionId");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
