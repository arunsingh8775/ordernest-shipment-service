package com.ordernest.shipment.messaging;

import com.ordernest.shipment.event.ShipmentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShipmentEventPublisher {

    private final KafkaTemplate<String, ShipmentEvent> kafkaTemplate;

    @Value("${app.kafka.topic.shipment-events}")
    private String shipmentEventsTopic;

    public void publish(ShipmentEvent shipmentEvent) {
        kafkaTemplate.send(shipmentEventsTopic, shipmentEvent.orderId(), shipmentEvent)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish shipment event for orderId={}", shipmentEvent.orderId(), ex);
                    } else {
                        log.info("Shipment event published successfully. topic={}, partition={}, offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
