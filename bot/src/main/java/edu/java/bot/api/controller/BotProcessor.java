package edu.java.bot.api.controller;

import com.pengrad.telegrambot.model.Update;

public interface BotProcessor {

    void process(Update update);

}
