package edu.java.bot.core.telegram.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.core.communication.dialog.Dialog;
import edu.java.bot.core.repository.LinkRepository;
import edu.java.bot.core.telegram.Bot;
import edu.java.bot.entity.Link;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotService {

    private final Bot bot;

    private final LinkRepository linkRepository;

    private final Map<Long, Dialog> dialogs;

    @Autowired
    public BotService(Bot bot, LinkRepository linkRepository) {
        this.bot = bot;
        this.linkRepository = linkRepository;
        this.dialogs = new HashMap<>();
    }

    public void sendMessage(String message, Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, message));
    }

    public void sendHtmlMessage(String message, Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, message)
            .parseMode(ParseMode.HTML));
    }

    public void sendMdMessage(String message, Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, message)
            .parseMode(ParseMode.Markdown));
    }

    public void addDialog(Update update, Dialog dialog) {
        dialogs.put(update.message().from().id(), dialog);
    }

    public boolean isUserInDialog(Update update) {
        return dialogs.containsKey(update.message().from().id());
    }

    public void consumeDialog(Update update) {
        Long userId = update.message().from().id();
        Dialog dialog = dialogs.get(userId);
        var commandResult = dialog.peek().execute(this, update);
        if (commandResult) {
            dialog.pop();
            if (dialog.isEmpty()) {
                dialogs.remove(userId);
            }
        }
    }

    public void saveLink(Update update, Link link) {
        Long userId = update.message().from().id();
        linkRepository.save(userId, link);
    }

    public void removeLink(Update update, String link) {
        Long userId = update.message().from().id();
        linkRepository.delete(userId, link);
    }

    public List<Link> getAllLinks(Update update) {
        Long userId = update.message().from().id();
        return linkRepository.getAll(userId);
    }

}
