package edu.java.bot.core.telegram.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.core.repository.LinkRepository;
import edu.java.bot.core.telegram.TraceBot;
import edu.java.bot.core.communication.dialog.Dialog;
import edu.java.bot.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BotService {

    private final TraceBot bot;

    private final LinkRepository linkRepository;

    private final Map<Long, Dialog> dialogs;

    @Autowired
    public BotService(TraceBot bot, LinkRepository linkRepository) {
        this.bot = bot;
        this.linkRepository = linkRepository;
        this.dialogs = new HashMap<>();
    }

    public void sendMessage(String message, Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, message));
    }

    public void addDialog(Update update, Dialog dialog) {
        dialogs.put(update.message().from().id(), dialog);
    }

    public boolean isUserInDialog(Update update) {
        return dialogs.containsKey(update.message().from().id());
    }

    public void consumeDialog(Update update) {
        Long userId = update.message().from().id();
        Dialog dialog = dialogs.getOrDefault(userId, null);
        dialog.pop().execute(this, update);
        if (dialog.isEmpty()) {
            dialogs.remove(userId);
        }
    }

    public void saveLink(Update update, String link) {
        Long userId = update.message().from().id();
        linkRepository.save(userId, new Link(link));
    }

    public List<Link> getAllLinks(Update update) {
        Long userId = update.message().from().id();
        return linkRepository.getAll(userId);
    }

}
