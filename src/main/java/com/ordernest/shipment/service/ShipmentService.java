package com.ordernest.shipment.service;

import com.ordernest.shipment.dto.UpdateShipmentStatusRequest;
import com.ordernest.shipment.event.ShipmentEvent;
import com.ordernest.shipment.messaging.ShipmentEventPublisher;
import com.ordernest.shipment.security.JwtService;
import java.time.Instant;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentEventPublisher shipmentEventPublisher;
    private final JwtService jwtService;

    public void publishShipmentStatus(UpdateShipmentStatusRequest request, String authorization) {
        String token = extractBearerToken(authorization);
        if (!jwtService.isTokenValid(token)) {
            throw new AccessDeniedException("Invalid or expired token");
        }

        String normalizedRole = normalizeRole(jwtService.extractRole(token));
        if (!"ROLE_ADMIN".equals(normalizedRole)) {
            throw new AccessDeniedException("Only admin can update shipment status");
        }

        String updatedBy = jwtService.extractEmail(token);
        ShipmentEvent shipmentEvent = new ShipmentEvent(
                request.orderId(),
                request.shipmentStatus(),
                updatedBy,
                Instant.now()
        );
        shipmentEventPublisher.publish(shipmentEvent);
    }

    private String extractBearerToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new AccessDeniedException("Missing or invalid Authorization header");
        }
        return authorization.substring(7);
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            throw new AccessDeniedException("Role claim missing in token");
        }

        String trimmedRole = role.trim().toUpperCase(Locale.ROOT);
        if (!trimmedRole.startsWith("ROLE_")) {
            trimmedRole = "ROLE_" + trimmedRole;
        }
        return trimmedRole;
    }
}
