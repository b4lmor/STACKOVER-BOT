package edu.java.bot.core.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import edu.java.bot.api.filter.chain.BotFilterChain;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TraceBot extends TelegramBot {

    @Autowired
    public TraceBot(ApplicationConfig applicationConfig) {
        super(applicationConfig.telegramToken());
    }

    public void run(BotFilterChain botFilterChain) {
        this.setUpdatesListener(updates -> {
            updates.forEach(botFilterChain::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

}
