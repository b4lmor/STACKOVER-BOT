package edu.java.bot.api.scrapper.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.request.ParseMode;
import edu.java.bot.api.scrapper.dlq.DeadLetterQueue;
import edu.java.bot.api.scrapper.dto.request.UpdateDto;
import edu.java.bot.core.telegram.service.BotService;
import edu.java.bot.core.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class ScrapperKafkaListener {

    private final BotService botService;

    private final DeadLetterQueue deadLetterQueue;

    @SneakyThrows
    @KafkaListener(
        topics = "messages.scrapper",
        groupId = "messages-group"
    )
    public void listenProtobufMessages(String jsonUpdate) {
        UpdateDto update = new ObjectMapper().readValue(jsonUpdate, UpdateDto.class);
        if (Validator.isValidUpdateDto(update)) {
            log.info("Sending update ...");
            botService.sendUpdate(update, ParseMode.HTML);
        } else {
            log.error("Update is invalid: {}", jsonUpdate);
            deadLetterQueue.send(jsonUpdate);
        }
    }

}
