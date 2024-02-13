package edu.java.bot.api.filter.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.controller.Controller;
import edu.java.bot.api.controller.impl.MessageController;
import edu.java.bot.api.filter.BotFilter;
import edu.java.bot.api.filter.FilterPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFilter implements BotFilter {

    private final Controller controller;

    @Autowired
    public MessageFilter(MessageController controller) {
        this.controller = controller;
    }

    @Override
    public void doFilter(Update update) {
        //update.
    }

    @Override
    public FilterPriority getPriority() {
        return FilterPriority.MEDIUM;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
