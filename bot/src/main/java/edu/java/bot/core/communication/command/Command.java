package edu.java.bot.core.communication.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.telegram.service.BotService;

public interface Command {

    void execute(BotService botService, Update update, String... args);

    void setNext(Command command);

}
