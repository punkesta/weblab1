package com.noname.weblabs.lab6.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

import com.noname.weblabs.health.ErrorResponse;
import com.noname.weblabs.lab6.dto.OrderDto;
import com.noname.weblabs.lab6.service.IdempotencyService;

@RestController
public class OrderRestController {
    @Autowired
    private IdempotencyService idempotencyService;

    @PostMapping("/orders/create")
    public ResponseEntity<?> createOrderDto(
            @RequestHeader(value="Idempotency-Key", required=false)
            String idempotencyKey,
            @RequestHeader(value="X-Request-Id", required=false)
            String requestId,
            @RequestBody(required=false)
            Map<String, Object> body
    ) {
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            var errorResponse = new ErrorResponse(
                    "Idempotency-Key header not found", null, null, requestId
            );

            return ResponseEntity.status(400)
                                 .body(errorResponse);
        }

        var existingResponse = (OrderDto) idempotencyService.get(idempotencyKey);
        if (existingResponse != null) {
            var existingResponseBody = createResponseBody(
                    existingResponse.id(), existingResponse.name(), requestId
            );

            return ResponseEntity.status(201)
                                 .header("X-Request-Id", requestId)
                                 .body(existingResponseBody);
        }

        var orderId = "order_" + UUID.randomUUID();
        var orderName = (body != null && body.get("name") != null) ?
                        body.get("name").toString()
                        : "Noname";

        var order = idempotencyService.saveIfNotExists(
                idempotencyKey, () -> new OrderDto(orderId, orderName)
        );

        var responseBody = createResponseBody(order.id(), order.name(), requestId);
        return ResponseEntity.status(201)
                             .header("X-Request-Id", requestId)
                             .body(responseBody);
    }

    private Map<String, String> createResponseBody(String id, String name, String requestId) {
        return Map.of("id", id, "name", name, "requestId", requestId);
    }

    @GetMapping("/health")
    public ResponseEntity<?> health(
            @RequestParam(value="delayMs", required=false, defaultValue="0")
            long delayMs
    ) {
        if (delayMs > 0) {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}