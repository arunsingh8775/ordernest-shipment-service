package com.ordernest.shipment.service;

import com.ordernest.shipment.dto.UpdateShipmentStatusRequest;
import com.ordernest.shipment.event.ShipmentEvent;
import com.ordernest.shipment.messaging.ShipmentEventPublisher;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentEventPublisher shipmentEventPublisher;

    public void publishShipmentStatus(UpdateShipmentStatusRequest request, String updatedBy) {
        ShipmentEvent shipmentEvent = new ShipmentEvent(
                request.orderId(),
                request.shipmentStatus(),
                updatedBy,
                Instant.now()
        );
        shipmentEventPublisher.publish(shipmentEvent);
    }
}
