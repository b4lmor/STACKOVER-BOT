package edu.java.bot.core.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.api.telegram.filter.chain.BotFilterChain;
import edu.java.bot.configuration.ApplicationConfig;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import static edu.java.bot.api.telegram.Commands.getCommands;

@Component
@Log4j2
public class Bot extends TelegramBot {

    private final ApplicationContext ctx;

    @Autowired
    public Bot(ApplicationContext ctx) {
        super(ctx.getBean(ApplicationConfig.class).telegramToken());
        this.ctx = ctx;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        this.execute(new SetMyCommands(getCommands()));
        var botFilterChain = ctx.getBean(BotFilterChain.class);
        this.setUpdatesListener(updates -> {
            updates.forEach(botFilterChain::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                log.error("[Bot exception] :: Code: {}; Message: {}.",
                    e.response().errorCode(),
                    e.response().description()
                );
            } else {
                log.error("[Unexpected exception] :: Message: {}.", e.getMessage());
            }
        });
    }

    @PreDestroy
    public void stop() {
        this.shutdown();
    }

}
