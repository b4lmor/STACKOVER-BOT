package edu.java.bot.core.telegram.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.api.scrapper.client.ScrapperClient;
import edu.java.bot.api.scrapper.dto.request.LinkDto;
import edu.java.bot.api.scrapper.dto.request.UntrackLinkDto;
import edu.java.bot.api.scrapper.dto.request.UpdateDto;
import edu.java.bot.api.scrapper.dto.response.LinkViewDto;
import edu.java.bot.core.communication.dialog.Dialog;
import edu.java.bot.core.telegram.Bot;
import edu.java.bot.entity.Link;
import edu.java.bot.util.PrettifyUtils;
import jakarta.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BotService {

    private final Bot bot;

    private final ScrapperClient scrapperClient;

    private final Map<Long, Dialog> dialogs = new HashMap<>();

    public boolean isChatOpened(Update update) {
        long chatId = update.message().chat().id();
        return scrapperClient.checkChat(chatId);
    }

    public void openChat(Update update) {
        long chatId = update.message().chat().id();
        scrapperClient.openChat(chatId);
    }

    public void trackLink(Update update, Link link) {
        long chatId = update.message().chat().id();
        scrapperClient.trackLink(
            LinkDto.builder()
                .chatId(chatId)
                .value(link.getValue())
                .shortName(link.getName())
                .build()
        );
    }

    public void untrackLink(Update update, String linkName) {
        long chatId = update.message().chat().id();
        scrapperClient.untrackLink(
            UntrackLinkDto.builder()
                .chatId(chatId)
                .shortName(linkName)
                .build()
        );
    }

    public List<LinkViewDto> getAllLinks(Update update) {
        long chatId = update.message().chat().id();
        return scrapperClient.getLinksForChat(chatId);
    }

    public void sendMessage(String message, Update update, @Nullable ParseMode parseMode) {
        long chatId = update.message().chat().id();
        bot.execute(
            parseMode == null
                ? new SendMessage(chatId, message)
                : new SendMessage(chatId, message).parseMode(parseMode)
        );
    }

    public void sendMessage(String message, long chatId, @Nullable ParseMode parseMode) {
        bot.execute(
            parseMode == null
                ? new SendMessage(chatId, message)
                : new SendMessage(chatId, message).parseMode(parseMode)
        );
    }

    public void sendUpdates(List<UpdateDto> updateDtos, @Nullable ParseMode parseMode) {
        updateDtos.forEach(
            updateDto -> {
                try {
                    sendMessage(
                        PrettifyUtils.prettifyUpdate(updateDto),
                        updateDto.getChatId(),
                        parseMode
                    );
                } catch (Throwable throwable) {
                    log.error(
                        "[BOT SERVICE] :: Can't send a message to {}, reason: {}.",
                        updateDto.getChatId(),
                        throwable
                    );
                }
            });
    }

    public void addDialog(Update update, Dialog dialog) {
        long chatId = update.message().chat().id();
        dialogs.put(chatId, dialog);
    }

    public boolean isUserInDialog(Update update) {
        long chatId = update.message().chat().id();
        return dialogs.containsKey(chatId);
    }

    public void consumeDialog(Update update) {

        long chatId = update.message().chat().id();
        Dialog dialog = dialogs.get(chatId);
        boolean commandResult = dialog.peek().execute(this, update);

        if (commandResult) {
            dialog.pop();

            if (dialog.isEmpty()) {
                dialogs.remove(chatId);
            }
        }

    }

}
