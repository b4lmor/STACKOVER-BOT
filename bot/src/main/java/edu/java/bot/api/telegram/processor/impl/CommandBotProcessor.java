package edu.java.bot.api.telegram.processor.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import edu.java.bot.api.telegram.processor.BotProcessor;
import edu.java.bot.core.communication.dialog.BotDialogs;
import edu.java.bot.core.telegram.service.BotService;
import edu.java.bot.util.PrettifyUtils;
import edu.java.bot.util.ProcessorUtils;
import edu.java.bot.util.annotation.BotHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static edu.java.bot.api.telegram.Commands.HELP_COMMAND;
import static edu.java.bot.api.telegram.Commands.LIST_COMMAND;
import static edu.java.bot.api.telegram.Commands.START_COMMAND;
import static edu.java.bot.api.telegram.Commands.TRACK_COMMAND;
import static edu.java.bot.api.telegram.Commands.UNTRACK_COMMAND;

@Component
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommandBotProcessor implements BotProcessor {

    private final BotService botService;

    @Override
    public void process(Update update) {
        try {
            ProcessorUtils.findHandler(update.message().text(), "handleOther")
                .invoke(this, update);
        } catch (Exception e) {
            log.error(e.getMessage() == null ? e : e.getMessage());
        }
    }

    @BotHandler(START_COMMAND)
    public void handleStart(Update update) {
        botService.openChat(update);
        botService.sendMessage("Let's start!", update, null);
    }

    @BotHandler(HELP_COMMAND)
    public void handleHelp(Update update) {
        botService.sendMessage("Help command", update, null);
    }

    @BotHandler(TRACK_COMMAND)
    public void handleTrack(Update update) {
        botService.sendMessage("Send the name of the topic you want to subscribe to.", update, null);
        botService.addDialog(update, BotDialogs.newAddLinkDialog());
    }

    @BotHandler(UNTRACK_COMMAND)
    public void handleUntrack(Update update) {
        botService.sendMessage("Send the link you want to delete from your subscriptions.", update, null);
        botService.addDialog(update, BotDialogs.newRemoveLinkDialog());
    }

    @BotHandler(LIST_COMMAND)
    public void handleList(Update update) {
        var links = botService.getAllLinks(update);
        botService.sendMessage(PrettifyUtils.prettifyLinks(links), update, ParseMode.HTML);
    }

    public void handleOther(Update update) {
        botService.sendMessage("Unknown command", update, null);
    }

}
