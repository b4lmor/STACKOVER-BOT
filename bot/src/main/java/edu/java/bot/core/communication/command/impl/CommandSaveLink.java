package edu.java.bot.core.communication.command.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.communication.command.Command;
import edu.java.bot.core.telegram.service.BotService;
import edu.java.bot.entity.Link;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommandSaveLink extends Command {

    private final Link link;

    public CommandSaveLink(Link link) {
        this.link = link;
    }

    @Override
    protected boolean start(BotService botService, Update update) {
        botService.saveLink(update, link);
        return true;
    }
}
