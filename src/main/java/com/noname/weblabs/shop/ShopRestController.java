package com.noname.weblabs.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.noname.weblabs.health.ErrorResponse;
import com.noname.weblabs.inventory.entity.BaseItemEntity;
import com.noname.weblabs.lab6.service.IdempotencyService;

@RestController
@RequestMapping("/api/shop")
public class ShopRestController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private IdempotencyService idempotencyService;

    @GetMapping
    public List<BaseItemEntity> getItems() {
        return shopService.getAllBaseItems();
    }

    @PostMapping("/test-buy")
    public ResponseEntity<?> testBuy(
            /*
            @AuthenticationPrincipal
            CustomUserDetails user,
            */
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

        var existingData = (ShopBuyDto) idempotencyService.get(idempotencyKey);
        if (existingData != null) {
            var existingResponseBody = createResponseBody(
                    existingData.id(), existingData.baseItemId(), requestId
            );

            return ResponseEntity.status(201)
                                 .header("X-Request-Id", requestId)
                                 .body(existingResponseBody);
        }

        var buyId = "buy_" + UUID.randomUUID();
        var baseItemId = body.get("baseItemId").toString();

        // shopService.buyItem(user.getUserId(), baseItemId);

        var buyData = idempotencyService.saveIfNotExists(
                idempotencyKey, () -> new ShopBuyDto(buyId, baseItemId)
        );

        var responseBody = createResponseBody(buyData.id(), buyData.baseItemId(), requestId);
        return ResponseEntity.status(201)
                             .header("X-Request-Id", requestId)
                             .body(responseBody);
    }

    private Map<String, String> createResponseBody(
            String buyId, String baseItemId, String requestId
    ) {
        return Map.of("id", buyId, "baseItemId", baseItemId, "requestId", requestId);
    }
}