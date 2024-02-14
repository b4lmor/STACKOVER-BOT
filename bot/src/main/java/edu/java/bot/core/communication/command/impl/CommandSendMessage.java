package edu.java.bot.core.communication.command.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.exception.BotException;
import edu.java.bot.core.telegram.service.BotService;
import edu.java.bot.core.communication.command.Command;

public class CommandSendMessage implements Command {

    private final String message;

    private Command next = null;

    public CommandSendMessage(String message) {
        this.message = message;
    }

    @Override
    public void execute(BotService botService, Update update, String... args) {
        if (args.length != 0) {
            throw new BotException(
                "Illegal arguments",
                "'CommandSendMessage' can handle exactly 1 argument."
            );
        }
        botService.sendMessage(message, update);
        if (next != null) {
            next.execute(botService, update, args);
        }
    }

    @Override
    public void setNext(Command command) {
        this.next = command;
    }

}
