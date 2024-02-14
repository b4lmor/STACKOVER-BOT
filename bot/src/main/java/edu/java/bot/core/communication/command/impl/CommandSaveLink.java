package edu.java.bot.core.communication.command.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.exception.BotException;
import edu.java.bot.core.communication.command.Command;
import edu.java.bot.core.telegram.service.BotService;

public class CommandSaveLink implements Command {

    private Command next = null;

    @Override
    public void execute(BotService botService, Update update, String... args) {
        if (args.length != 0) {
            throw new BotException(
                "Illegal arguments",
                "'CommandSendMessage' can handle exactly 1 argument."
            );
        }
        botService.saveLink(update, update.message().text());
        if (next != null) {
            next.execute(botService, update, args);
        }
    }

    @Override
    public void setNext(Command command) {
        this.next = command;
    }

}
