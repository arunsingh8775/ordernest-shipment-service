package com.ordernest.shipment.controller;

import com.ordernest.shipment.dto.UpdateShipmentStatusRequest;
import com.ordernest.shipment.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping("/status")
    public ResponseEntity<Void> updateShipmentStatus(
            @Valid @RequestBody UpdateShipmentStatusRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        shipmentService.publishShipmentStatus(request, authorization);
        return ResponseEntity.accepted().build();
    }
}
