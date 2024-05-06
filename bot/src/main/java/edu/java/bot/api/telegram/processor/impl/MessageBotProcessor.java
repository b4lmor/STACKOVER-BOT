package edu.java.bot.api.telegram.processor.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.telegram.processor.BotProcessor;
import edu.java.bot.core.telegram.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static edu.java.bot.api.telegram.Commands.HELP_COMMAND;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageBotProcessor implements BotProcessor {

    private final BotService botService;

    @Override
    public void process(Update update) {
        if (botService.isUserInDialog(update)) {
            botService.consumeDialog(update);
        } else {
            botService.sendMessage(
                "How can I help you? Send a command or use " + HELP_COMMAND + ".",
                update,
                null
            );
        }
    }
}
