package com.noname.weblabs.health;

public record ErrorResponse(
        String error,
        String code,
        Object details,
        String requestId
) {}