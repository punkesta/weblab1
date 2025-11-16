package com.noname.weblabs.lab6.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Order(1)
@Component
public class XRequestIdFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain
    ) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;

        var idHeader = httpRequest.getHeader("X-Request-Id");
        if (idHeader == null || idHeader.isBlank()) {
            idHeader = UUID.randomUUID().toString();
        }

        httpRequest.setAttribute("X-Request-Id", idHeader);
        httpResponse.setHeader("X-Request-Id", idHeader);

        chain.doFilter(httpRequest, httpResponse);
    }
}
