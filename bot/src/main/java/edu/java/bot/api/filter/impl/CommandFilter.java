package edu.java.bot.api.filter.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.controller.Controller;
import edu.java.bot.api.controller.impl.CommandController;
import edu.java.bot.api.filter.BotFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CommandFilter implements BotFilter {

    private final Controller controller;

    @Autowired
    public CommandFilter(CommandController controller) {
        this.controller = controller;
    }

    @Override
    public void doFilter(Update update) {
        if (update.message().text().startsWith("/")) {
            controller.process(update);
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
