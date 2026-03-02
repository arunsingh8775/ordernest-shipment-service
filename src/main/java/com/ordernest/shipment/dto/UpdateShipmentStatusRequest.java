package com.ordernest.shipment.dto;

import com.ordernest.shipment.event.ShipmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateShipmentStatusRequest(
        @NotBlank(message = "orderId is required")
        String orderId,
        @NotNull(message = "shipmentStatus is required")
        ShipmentStatus shipmentStatus
) {
}
