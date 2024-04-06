package edu.java.bot.api.scrapper.listener;

import java.util.List;
import edu.java.bot.api.scrapper.dto.request.UpdateDto;
import edu.java.bot.core.telegram.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScrapperKafkaListener {

    private final BotService botService;

    @KafkaListener(
        topics = "messages.scrapper",
        groupId = "messages-group",
        containerFactory = "protobufMessageKafkaListenerContainerFactory"
    )
    public void listenProtobufMessages(List<UpdateDto> updates) {
        botService.
    }

}
