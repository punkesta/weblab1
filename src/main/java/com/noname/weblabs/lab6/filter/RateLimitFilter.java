package com.noname.weblabs.lab6.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import com.google.gson.Gson;

import com.noname.weblabs.health.ErrorResponse;

@Order(2)
@Component
public class RateLimitFilter implements Filter {
    private static final int MAX_REQUEST_COUNT = 8;
    private static final long REQUEST_INTERVAL_MILLISECONDS = 10000;

    private final Map<String, RequestStats> statsByIp = new ConcurrentHashMap<>();

    @Autowired
    private Gson gson;

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain
    ) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;

        var ip = httpRequest.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = httpRequest.getRemoteAddr() == null ? "local" : httpRequest.getRemoteAddr();
        }

        var currentTime = Instant.now().toEpochMilli();

        var requestStats = statsByIp.getOrDefault(ip, new RequestStats(0, currentTime));
        if (currentTime - requestStats.time < REQUEST_INTERVAL_MILLISECONDS) {
            requestStats.count += 1;
        } else {
            requestStats.count = 1;
            requestStats.time = currentTime;
        }

        statsByIp.put(ip, requestStats);

        if (requestStats.count > MAX_REQUEST_COUNT) {
            httpResponse.setHeader("Retry-After", "2");
            httpResponse.setStatus(429);

            var requestId = (String) httpRequest.getAttribute("X-Request-Id");
            var errorResponse = new ErrorResponse("too_many_requests", null, null, requestId);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(gson.toJson(errorResponse));

            return;
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    private static class RequestStats {
        int count;
        long time;
        RequestStats(int count, long time)
        {
            this.count =count;
            this.time =time;
        }
    }
}
