package edu.java.bot.core.communication.command.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.communication.command.Command;
import edu.java.bot.core.telegram.service.BotService;
import edu.java.bot.core.validation.Validator;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommandRemoveLink extends Command {

    @Override
    protected boolean start(BotService botService, Update update) {
        String linkName = update.message().text();
        if (!Validator.isValidLinkName(linkName)) {
            return false;
        }
        botService.untrackLink(update, linkName);
        return true;
    }
}
