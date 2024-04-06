package edu.java.scrapper.configuration;

import edu.java.scrapper.api.bot.client.BotClient;
import edu.java.scrapper.api.bot.dto.response.UpdateDto;
import edu.java.scrapper.api.bot.producer.Producer;
import edu.java.scrapper.api.bot.producer.impl.HttpProducer;
import edu.java.scrapper.api.bot.producer.impl.KafkaProducer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class DataTransferConfiguration {

    @Autowired
    @Bean
    @ConditionalOnProperty(name = "app.data-transfer-protocol", havingValue = "http")
    public Producer httpProducer(BotClient botClient) {
        return new HttpProducer(botClient);
    }

    @Autowired
    @Bean
    @ConditionalOnProperty(name = "app.data-transfer-protocol", havingValue = "kafka")
    public Producer kafkaProducer(KafkaTemplate<String, List<UpdateDto>> kafkaTemplate) {
        return new KafkaProducer(kafkaTemplate);
    }

}
