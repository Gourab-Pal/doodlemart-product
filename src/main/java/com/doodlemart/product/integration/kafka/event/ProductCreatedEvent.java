package com.doodlemart.product.integration.kafka.event;

import java.util.UUID;

public record ProductCreatedEvent(
        UUID productId
) {
}
