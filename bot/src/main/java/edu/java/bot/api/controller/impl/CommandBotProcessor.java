package edu.java.bot.api.controller.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.controller.BotProcessor;
import edu.java.bot.core.communication.dialog.BotDialogs;
import edu.java.bot.core.telegram.service.BotService;
import edu.java.bot.util.ControllerUtils;
import edu.java.bot.util.PrettifyUtils;
import edu.java.bot.util.annotation.BotHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static edu.java.bot.api.Commands.HELP_COMMAND;
import static edu.java.bot.api.Commands.LIST_COMMAND;
import static edu.java.bot.api.Commands.START_COMMAND;
import static edu.java.bot.api.Commands.TRACK_COMMAND;
import static edu.java.bot.api.Commands.UNTRACK_COMMAND;

@Component
@Log4j2
public class CommandBotProcessor implements BotProcessor {

    private final BotService botService;

    @Autowired
    public CommandBotProcessor(BotService botService) {
        this.botService = botService;
    }

    @Override
    public void process(Update update) {
        try {
            ControllerUtils.findHandler(update.message().text(), "handleOther")
                .invoke(this, update);
        } catch (Exception e) {
            log.error(e.getMessage() == null ? e : e.getMessage());
        }
    }

    @BotHandler(START_COMMAND)
    public void handleStart(Update update) {
        botService.sendMessage("Start command", update);
    }

    @BotHandler(HELP_COMMAND)
    public void handleHelp(Update update) {
        botService.sendMessage("Help command", update);
    }

    @BotHandler(TRACK_COMMAND)
    public void handleTrack(Update update) {
        botService.sendMessage("Send the name of the topic you want to subscribe to.", update);
        botService.addDialog(update, BotDialogs.newAddLinkDialog());
    }

    @BotHandler(UNTRACK_COMMAND)
    public void handleUntrack(Update update) {
        botService.sendMessage("Send the link you want to delete from your subscriptions.", update);
        botService.addDialog(update, BotDialogs.newRemoveLinkDialog());
    }

    @BotHandler(LIST_COMMAND)
    public void handleList(Update update) {
        var links = botService.getAllLinks(update);
        botService.sendHtmlMessage(PrettifyUtils.prettifyLinks(links), update);
    }

    public void handleOther(Update update) {
        botService.sendMessage("Unknown command", update);
    }

}
