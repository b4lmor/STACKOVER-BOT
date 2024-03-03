package edu.java.bot.core.telegram.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.core.communication.dialog.Dialog;
import edu.java.bot.core.repository.LinkRepository;
import edu.java.bot.core.telegram.Bot;
import edu.java.bot.entity.Link;
import jakarta.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BotService {

    private final Bot bot;

    private final LinkRepository linkRepository;

    private final Map<Long, Dialog> dialogs = new HashMap<>();

    public boolean isChatOpened(Update update) {
        return true; // TODO: ask scrapper
    }

    public void openChat(Update update) {
        // TODO: send to scrapper's db
    }

    public void saveLink(Update update, Link link) {
        long chatId = update.message().chat().id();
        linkRepository.save(chatId, link);
    }

    public void sendMessage(String message, Update update, @Nullable ParseMode parseMode) {
        long chatId = update.message().chat().id();
        bot.execute(
            parseMode == null
                ? new SendMessage(chatId, message)
                : new SendMessage(chatId, message).parseMode(parseMode)
        );
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

    public void removeLink(Update update, String link) {
        long userId = update.message().from().id();
        linkRepository.delete(userId, link);
    }

    public List<Link> getAllLinks(Update update) {
        long userId = update.message().from().id();
        return linkRepository.getAll(userId);
    }

}
