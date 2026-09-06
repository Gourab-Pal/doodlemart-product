package com.doodlemart.product.integration.kafka;

import com.doodlemart.product.integration.kafka.event.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Component
public class ProductEventPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(ProductEventPublisher.class);

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

    public CompletableFuture<SendResult<String, ProductCreatedEvent>>
    publishProductCreated(ProductCreatedEvent event) {

        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
                kafkaTemplate.send(
                        productCreatedTopic,
                        event.productId().toString(),
                        event
                );

        future.whenComplete(
                new BiConsumer<SendResult<String, ProductCreatedEvent>, Throwable>() {
                    @Override
                    public void accept(
                            SendResult<String, ProductCreatedEvent> result,
                            Throwable exception
                    ) {
                        if (exception != null) {
                            log.error(
                                    "Failed to publish ProductCreated event for productId: {}",
                                    event.productId(),
                                    exception
                            );
                        } else {
                            log.info(
                                    "ProductCreated event published successfully for productId: {}",
                                    event.productId()
                            );
                        }
                    }
                }
        );

        return future;
    }
}