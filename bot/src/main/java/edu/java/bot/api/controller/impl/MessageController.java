package edu.java.bot.api.controller.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.controller.Controller;
import edu.java.bot.core.telegram.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageController implements Controller {

    private final BotService botService;

    @Autowired
    public MessageController(BotService botService) {
        this.botService = botService;
    }

    @Override
    public void process(Update update) {
        if (botService.isUserInDialog(update)) {
            botService.consumeDialog(update);
        } else {
            botService.sendMessage("How can I help you? Send a command.", update);
        }
    }
}
