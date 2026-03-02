# Ordernest Shipment Service

Shipment service publishes shipment status events to Kafka.

## Endpoint
- `POST /api/shipments/status` (admin only)

Request body:
```json
{
  "orderId": "00000000-0000-0000-0000-000000000000",
  "shipmentStatus": "CREATED"
}
```

Valid `shipmentStatus` values:
- `CREATED`
- `SHIPPED`
- `DELIVERED`

This service only publishes events. Order state changes are applied by `ordernest-order-service` after validation.
