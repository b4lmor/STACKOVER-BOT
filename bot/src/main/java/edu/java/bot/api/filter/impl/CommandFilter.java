package edu.java.bot.api.filter.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.controller.BotProcessor;
import edu.java.bot.api.controller.impl.CommandBotProcessor;
import edu.java.bot.api.filter.BotFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static edu.java.bot.api.Commands.COMMAND_PREFIX;

@Component
public class CommandFilter implements BotFilter {

    private final BotProcessor botProcessor;

    @Autowired
    public CommandFilter(CommandBotProcessor controller) {
        this.botProcessor = controller;
    }

    @Override
    public void doFilter(Update update) {
        if (update.message().text().startsWith(COMMAND_PREFIX)) {
            botProcessor.process(update);
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
