package com.example.api.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Enumeration;

import static com.example.api.Constants.X_REQUEST_ID;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@WebFilter("/")
public class XRequestFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(XRequestFilter.class);
    private static final String X_REQUEST_ID_MDC_NAME = "xRequestId";
    private static final String[] IGNORE_PATH = {
        "/health",
        "swagger"
    };

    @Override
    public void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain)
        throws ServletException, IOException {
        long startAt = Instant.now().toEpochMilli();
        try {
            String xRequestId = getXRequestId(httpRequest);
            if (xRequestId != null) {
                MDC.put(X_REQUEST_ID_MDC_NAME, String.format("[%s] ", xRequestId));
            }
            chain.doFilter(httpRequest, httpResponse);
        } finally {
            boolean is404NotFound = httpResponse.getStatus() == NOT_FOUND.value();

            // 404-е не логируем
            if (!is404NotFound && log.isInfoEnabled() && !StringUtils.containsAny(httpRequest.getRequestURI(), IGNORE_PATH)) {
                logRequest(httpRequest, startAt);
            }

            // Tear down MDC data: cleans up the ThreadLocal data
            MDC.remove(X_REQUEST_ID_MDC_NAME);
        }
    }

    private String getXRequestId(HttpServletRequest request) {
        String xRequestId = request.getHeader(X_REQUEST_ID);
        if (xRequestId == null) {
            xRequestId = getRequestIdIgnoreCase(request);
        }
        return xRequestId;
    }

    private String getRequestIdIgnoreCase(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            if (name != null && X_REQUEST_ID.compareToIgnoreCase(name) == 0) {
                return request.getHeader(name);
            }
        }
        return null;
    }

    private void logRequest(HttpServletRequest request, long startAt) {
        String queryString = request.getQueryString();
        queryString = queryString != null ? "?" + queryString : "";
        long duration = Instant.now().toEpochMilli() - startAt;
        log.info("Request: {}{}, ms {}", request.getRequestURI(), queryString, duration);
    }

}
