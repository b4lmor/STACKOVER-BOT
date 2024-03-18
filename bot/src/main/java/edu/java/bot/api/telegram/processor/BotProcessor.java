package edu.java.bot.api.telegram.processor;

import com.pengrad.telegrambot.model.Update;

public interface BotProcessor {

    void process(Update update);

}
