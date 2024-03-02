package edu.java.bot.api.filter;

import com.pengrad.telegrambot.model.Update;

public interface BotFilter {

    void doFilter(Update update);

    default boolean isEnabled() {
        return false;
    }

}
