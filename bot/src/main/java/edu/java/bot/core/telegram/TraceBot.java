package edu.java.bot.core.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import edu.java.bot.api.filter.chain.BotFilterChain;
import edu.java.bot.configuration.ApplicationConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class TraceBot extends TelegramBot {

    private final ApplicationContext ctx;

    @Autowired
    public TraceBot(ApplicationContext ctx) {
        super(ctx.getBean(ApplicationConfig.class).telegramToken());
        this.ctx = ctx;
    }

    public void run() {
        var botFilterChain = ctx.getBean(BotFilterChain.class);
        this.setUpdatesListener(updates -> {
            updates.forEach(botFilterChain::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                log.error("[Bot exception] Code: {}; Message: {}.",
                    e.response().errorCode(),
                    e.response().description()
                );
            } else {
                log.error("[Unexpected exception] Message: {}.", e.getMessage());
            }
        });
    }

}
