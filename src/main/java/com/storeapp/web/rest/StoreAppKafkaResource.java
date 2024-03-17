package com.storeapp.web.rest;

import com.storeapp.broker.KafkaConsumer;
import java.security.Principal;
import java.util.List;

import com.storeapp.domain.Ingredients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@RestController
@RequestMapping("/api/store-app-kafka")
public class StoreAppKafkaResource {

    private static final String PRODUCER_BINDING_NAME = "sandwich-topic";

    private final Logger log = LoggerFactory.getLogger(StoreAppKafkaResource.class);
    private final KafkaConsumer kafkaConsumer;
    private final StreamBridge streamBridge;

    public StoreAppKafkaResource(StreamBridge streamBridge, KafkaConsumer kafkaConsumer) {
        this.streamBridge = streamBridge;
        this.kafkaConsumer = kafkaConsumer;
    }

    @PostMapping("/order")
    public void order(@RequestBody List<Ingredients> ingredientsDTO) {
        log.debug("REST request the message : {} to send to Kafka topic ", ingredientsDTO);

        streamBridge.send(PRODUCER_BINDING_NAME, ingredientsDTO);
    }
    @PostMapping("/publish")
    public void publish(@RequestParam("message") String message) {
        log.debug("REST request the message : {} to send to Kafka topic ", message);
        streamBridge.send("binding-out-0", message);
    }

    @GetMapping("/register")
    public ResponseBodyEmitter register(Principal principal) {
        return kafkaConsumer.register("sse-topic-0");
    }

    @GetMapping("/unregister")
    public void unregister(Principal principal) {
        kafkaConsumer.unregister(principal.getName());
    }
}
