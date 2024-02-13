package edu.java.bot.api.controller;

import com.pengrad.telegrambot.model.Update;

public interface Controller {

    void process(Update update);

}
