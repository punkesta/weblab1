package com.noname.weblabs.lab6.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import com.google.gson.Gson;

import com.noname.weblabs.health.ErrorResponse;

//@Order(3)
//@Component
public class FailImitationFilter implements Filter {
    private final Random random = new Random();

    @Autowired
    private Gson gson;

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain
    ) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;

        var num = random.nextDouble();

        try {
            if (num < 0.15) {
                Thread.sleep(1200 + random.nextInt(800));
            } else if (num > 0.80) {
                var isUnavailable = random.nextBoolean();

                httpResponse.setStatus(isUnavailable ? 503 : 500);

                var requestId = (String) httpRequest.getAttribute("X-Request-Id");
                var errorResponse = new ErrorResponse(
                        isUnavailable ? "unavailable" : "unexpected", null, null, requestId
                );
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write(gson.toJson(errorResponse));

                return;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        chain.doFilter(servletRequest, servletResponse);
    }
}
