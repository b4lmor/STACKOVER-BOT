package edu.java.scrapper.api.bot.producer.impl;

import edu.java.scrapper.api.bot.dto.response.UpdateDto;
import edu.java.scrapper.api.bot.producer.Producer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaProducer implements Producer {

    private static final String TOPIC_NAME = "messages.scrapper";

    private final KafkaTemplate<String, List<UpdateDto>> kafkaTemplate;

    @Override
    public void sendUpdates(List<UpdateDto> updates) {
        kafkaTemplate.send(TOPIC_NAME, updates);
    }

}
