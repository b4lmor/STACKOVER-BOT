package edu.java.bot.api.scrapper.dlq;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeadLetterQueue {

    private static final String DLQ_TOPIC = "messages.scrapper_dlq";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String update) {
        kafkaTemplate.send(DLQ_TOPIC, update);
    }

}
