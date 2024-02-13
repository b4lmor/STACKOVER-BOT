package edu.java.bot.core.telegram;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotService {

    private final TraceBot bot;

    @Autowired
    public BotService(TraceBot bot) {
        this.bot = bot;
    }

    public void sendMessage(String message, Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, message));
    }
}
