package com.ordernest.shipment.event;

import java.time.Instant;

public record ShipmentEvent(
        String orderId,
        ShipmentStatus shipmentStatus,
        String updatedBy,
        Instant timestamp
) {
}
