package com.doodlemart.product.integration.kafka;

import com.doodlemart.product.integration.kafka.event.ProductCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final String productCreatedTopic;

    public ProductEventPublisher(
            KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate,
            @Value("${doodlemart.kafka.topic.product-created}")
            String productCreatedTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.productCreatedTopic = productCreatedTopic;
    }

    public void publishProductCreated(ProductCreatedEvent event) {
        kafkaTemplate.send(
                productCreatedTopic,
                event.productId().toString(),
                event
        );
    }
}
