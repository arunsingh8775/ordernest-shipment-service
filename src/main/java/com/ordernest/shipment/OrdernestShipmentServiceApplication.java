package com.ordernest.shipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class OrdernestShipmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdernestShipmentServiceApplication.class, args);
    }
}
