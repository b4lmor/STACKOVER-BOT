package edu.java.scrapper.api.bot.producer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.scrapper.api.bot.dto.response.UpdateDto;
import edu.java.scrapper.api.bot.producer.Producer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaProducer implements Producer {

    private static final String TOPIC_NAME = "messages.scrapper";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    @Override
    public void sendUpdate(UpdateDto update) {
        kafkaTemplate.send(TOPIC_NAME, "updates", new ObjectMapper().writeValueAsString(update));
    }

}
