package edu.java.bot.core.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bot extends TelegramBot {
    @Autowired
    public Bot(ApplicationConfig applicationConfig) {
        super(applicationConfig.telegramToken());
    }

    public void run() {
        this.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                var chatId = update.message().from().id();
                this.execute(new SendMessage(chatId, "Hello, " + update.message().from().firstName()));
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
